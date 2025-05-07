package com.transplantados.authentication.exception;

import com.transplantados.shared.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class AuthenticationException extends ApiException {

    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, "AUTHORIZATION_EXCEPTION", "Unauthorized", Map.of());
    }

}
