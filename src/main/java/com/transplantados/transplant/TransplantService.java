package com.transplantados.transplant;

import com.transplantados.alert.AlertService;
import com.transplantados.patient.PatientRepository;
import com.transplantados.transplant.dto.CreateTransplantLogBookRequest;
import com.transplantados.transplant.dto.CreateTransplantRequest;
import com.transplantados.transplant.dto.UpdateTransplantRequest;
import com.transplantados.transplant.exception.TransplantNotFoundException;
import com.transplantados.variables.VariableInput;
import com.transplantados.variables.VariableRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TransplantService {

    private final TransplantRepository transplantRepository;

    private final VariableRepository variableRepository;

    private final PatientRepository patientRepository;

    private final TransplantLogBookRepository transplantLogBookRepository;

    private final AlertService alertService;

    public List<Transplant> findAll() {
        return transplantRepository.findAll();
    }

    public Transplant create(@NotBlank @Validated CreateTransplantRequest request) {

        val variables = variableRepository.findAllByIdIn(request.variables());

        return transplantRepository.save(Transplant.builder()
                .name(request.name())
                .variables(variables)
                .build());
    }

    public Transplant update(@NotBlank @Validated UpdateTransplantRequest request) {
        val transplant = transplantRepository.findById(request.id())
                .orElseThrow(() -> new TransplantNotFoundException(request.id()));

        val variables = variableRepository.findAllByIdIn(request.variables());

        transplant.setName(request.name());
        transplant.setVariables(variables);

        return transplantRepository.save(transplant);
    }

    public void remove(@NotBlank @Validated UUID id) {
        transplantRepository.removeById(id);
    }

    public void createLogBook(@NotBlank @Validated CreateTransplantLogBookRequest request) {
        Map<UUID, BigDecimal> values = request.variables()
                .stream()
                .collect(Collectors.toMap(CreateTransplantLogBookRequest.TransplantLogBookVariable::variable, CreateTransplantLogBookRequest.TransplantLogBookVariable::value));

        val patient = patientRepository.findById(request.patient())
                .orElseThrow(() -> new TransplantNotFoundException(request.patient()));

        val logBook = TransplantLogBook.builder()
                .createdAt(LocalDateTime.now())
                .patient(patient)
                .build();

        List<VariableInput> variables = variableRepository.findAllByIdIn(new ArrayList<>(values.keySet()))
                .stream()
                .map(variable -> {
                    val value = values.get(variable.getId());
                    return VariableInput.builder()
                            .variable(variable)
                            .value(value)
                            .logBook(logBook)
                            .build();
                }).toList();

        logBook.setInputs(variables);

        val savedLogBook = transplantLogBookRepository.save(logBook);

        alertService.evaluateAndNotify(savedLogBook);
    }

    public List<TransplantLogBook> findLogBooksByPatientId(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new TransplantNotFoundException(patientId);
        }
        return transplantLogBookRepository.findByPatientId(patientId);
    }
}
