package com.transplantados.medication;

import com.transplantados.medication.dto.CreateMedicationRequest;
import com.transplantados.patient.PatientRepository;
import com.transplantados.patient.exception.PatientNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationTakenRepository medicationTakenRepository;
    private final PatientRepository patientRepository;

    public Medication createForPatient(UUID patientId, CreateMedicationRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));

        Medication medication = Medication.builder()
                .name(request.name())
                .dosage(request.dosage())
                .patient(patient)
                .build();
        return medicationRepository.save(medication);
    }

    public List<Medication> findAllForPatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException(patientId);
        }
        return medicationRepository.findByPatientId(patientId);
    }

    public void remove(UUID medicationId) {
        if (!medicationRepository.existsById(medicationId)) {
            throw new RuntimeException("Medication not found");
        }
        medicationRepository.deleteById(medicationId);
    }

    public MedicationTaken markAsTaken(UUID medicationId) {
        var medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        MedicationTaken log = MedicationTaken.builder()
                .medication(medication)
                .taken(LocalDateTime.now())
                .build();

        return medicationTakenRepository.save(log);
    }
}