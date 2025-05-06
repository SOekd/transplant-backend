package com.transplantados.variables;

import com.transplantados.variables.dto.CreateVariableRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VariableService {

    private final VariableRepository variableRepository;

    public @NotNull Variable create(@Validated @NotNull CreateVariableRequest variable) {
        return variableRepository.save(Variable.builder()
                .name(variable.name())
                .unityOfMeasure(variable.unityOfMeasure())
                .deviceVariable(variable.deviceVariable())
                .isSwitch(variable.isSwitch())
                .showInLogBook(variable.showInLogBook())
                .build());
    }

    public void remove(@Validated @NotNull UUID id) {
        variableRepository.removeById(id);
    }

    public List<Variable> findAll() {
        return variableRepository.findAll();
    }

}
