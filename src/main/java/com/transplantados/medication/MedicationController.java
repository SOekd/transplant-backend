package com.transplantados.medication;


import com.transplantados.medication.dto.CreateMedicationRequest;
import com.transplantados.shared.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.Patient.MEDICATION_BASE_ROUTE)
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping(Routes.Patient.MEDICATION_CREATE)
    public ResponseEntity<Medication> create(@PathVariable UUID patientId, @RequestBody @Validated CreateMedicationRequest request) {
        return ResponseEntity.ok(medicationService.createForPatient(patientId, request));
    }

    @GetMapping(Routes.Patient.MEDICATION_GET_ALL)
    public ResponseEntity<List<Medication>> getAll(@PathVariable UUID patientId) {
        return ResponseEntity.ok(medicationService.findAllForPatient(patientId));
    }

    @DeleteMapping(Routes.Patient.MEDICATION_REMOVE)
    public ResponseEntity<Void> remove(@PathVariable UUID patientId, @PathVariable UUID medicationId) {
        medicationService.remove(medicationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(Routes.Patient.MEDICATION_CONSUME)
    public ResponseEntity<MedicationTaken> consume(@PathVariable UUID patientId, @PathVariable UUID medicationId) {
        return ResponseEntity.ok(medicationService.markAsTaken(medicationId));
    }
}