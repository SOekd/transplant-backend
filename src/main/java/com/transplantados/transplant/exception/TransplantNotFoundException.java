package com.transplantados.transplant.exception;

import com.transplantados.shared.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

public class TransplantNotFoundException extends ApiException {

    public TransplantNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "TRANSPLANT_NOT_FOUND", "Transplant not found", Map.of("id", id.toString()));
    }
}
