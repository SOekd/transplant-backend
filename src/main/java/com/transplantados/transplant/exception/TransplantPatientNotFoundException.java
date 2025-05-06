package com.transplantados.transplant.exception;

import com.transplantados.shared.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

public class TransplantPatientNotFoundException extends ApiException {

    public TransplantPatientNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "TRANSPLANT_PATIENT_NOT_FOUND", "Transplant patient not found", Map.of("id", id.toString()));
    }
}
