package com.transplantados.transplant.dto;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateTransplantLogBookRequest(
        @NotNull
        UUID patient,

        @NotNull List<@NotNull TransplantLogBookVariable> variables
) {

    public record TransplantLogBookVariable(
            @NotNull
            UUID variable,

            @NotNull
            BigDecimal value
    ) {
    }

}
