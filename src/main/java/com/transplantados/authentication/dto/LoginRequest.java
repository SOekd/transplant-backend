package com.transplantados.authentication.dto;

import org.jetbrains.annotations.NotNull;

public record LoginRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
