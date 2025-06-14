package com.transplantados.patient;

import com.transplantados.patient.dto.ChangePatientPasswordRequest;
import com.transplantados.patient.dto.CreatePatientRequest;
import com.transplantados.patient.dto.UpdatePatientRequest;
import com.transplantados.patient.dto.UpdatePatientTransplantsRequest;
import com.transplantados.shared.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(Routes.Patient.BASE_ROUTE)
@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping(Routes.Patient.CREATE)
    public ResponseEntity<Patient> create(@RequestBody @Validated CreatePatientRequest request) {
        return ResponseEntity.ok(patientService.create(request));
    }

    @PutMapping(Routes.Patient.UPDATE)
    public ResponseEntity<Patient> update(@RequestBody @Validated UpdatePatientRequest request) {
        return ResponseEntity.ok(patientService.update(request));
    }

    @DeleteMapping(Routes.Patient.REMOVE)
    public ResponseEntity<Void> remove(@PathVariable UUID patientId) {
        patientService.remove(patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(Routes.Patient.GET_ALL)
    public ResponseEntity<List<Patient>> getAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @PostMapping(Routes.Patient.CHANGE_PASSWORD)
    public ResponseEntity<Void> changePassword(@RequestBody @Validated ChangePatientPasswordRequest request) {
        patientService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(Routes.Patient.UPDATE_TRANSPLANTS)
    public ResponseEntity<Patient> updateTransplants(@PathVariable UUID patientId, @RequestBody @Validated UpdatePatientTransplantsRequest request) {
        return ResponseEntity.ok(patientService.updateTransplants(patientId, request));
    }

}
