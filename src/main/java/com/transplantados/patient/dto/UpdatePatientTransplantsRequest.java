package com.transplantados.patient.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdatePatientTransplantsRequest(
        @NotNull
        List<UUID> transplantIds
) {
}