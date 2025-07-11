package com.example.insurance_service_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private String error;
    private String message;
    private String details;
    private Instant timestamp;
}