package com.transplantados.shared.exception;

import java.util.Map;

public record ErrorResponse(
        String error,
        String message,
        Map<String, String> context
) {
}
