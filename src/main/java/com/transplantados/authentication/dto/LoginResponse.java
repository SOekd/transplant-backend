package com.transplantados.authentication.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
