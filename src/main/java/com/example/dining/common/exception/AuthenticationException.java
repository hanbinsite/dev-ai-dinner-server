package com.example.dining.common.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {
    private HttpStatus status = HttpStatus.UNAUTHORIZED;

    public AuthenticationException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}