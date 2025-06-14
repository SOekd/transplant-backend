package com.transplantados.medication.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMedicationRequest(
        @NotBlank
        String name,
        @NotBlank
        String dosage
) {
}