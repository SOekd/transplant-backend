package com.transplantados.patient.exception;

import com.transplantados.shared.exception.ApiException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

public class PatientNotFoundException extends ApiException {
    public PatientNotFoundException(@NotNull UUID id) {
        super(HttpStatus.NOT_FOUND, "PATIENT_NOT_FOUND", "Patient not found", Map.of("id", id.toString()));
    }
}
