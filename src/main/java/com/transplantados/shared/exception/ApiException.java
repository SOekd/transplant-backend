package com.transplantados.shared.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String error;
    private final Map<String, String> context;

    public ApiException(
            @NotNull HttpStatus httpStatus,
            @NotNull String error,
            @NotNull String message,
            @NotNull Map<String, String> context
    ) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = error;
        this.context = context;
    }

    public ApiException(
            @NotNull HttpStatus httpStatus,
            @NotNull String error,
            @NotNull String message
    ) {
        this(httpStatus, error, message, Map.of());
    }

}
