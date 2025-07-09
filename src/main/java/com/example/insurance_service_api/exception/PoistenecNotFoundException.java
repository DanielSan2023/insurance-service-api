package com.example.insurance_service_api.exception;

public class PoistenecNotFoundException extends RuntimeException {
    public PoistenecNotFoundException(String message) {
        super(message);
    }

    public PoistenecNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
