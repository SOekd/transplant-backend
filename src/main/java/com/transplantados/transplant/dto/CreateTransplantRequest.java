package com.transplantados.transplant.dto;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateTransplantRequest(
        @NotBlank
        String name,

        @NotNull List<@NotNull UUID> variables
) {
}
