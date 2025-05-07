package com.transplantados.patient.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ChangePatientPasswordRequest(
        @Id
        @GeneratedValue
        UUID id,

        @NotNull
        @NotBlank
        String password
) {
}
