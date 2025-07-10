package com.example.insurance_service_api.exception;

import com.example.insurance_service_api.utility.PoistenecConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input.");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, PoistenecConstants.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(PoistenecNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(PoistenecNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, PoistenecConstants.POISTENEC_NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, PoistenecConstants.ENTITY_NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, PoistenecConstants.BAD_REQUEST, e.getMessage(), null);
    }

    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String error, String message, String details) {
        ApiError response = new ApiError(status.value(), error, message, details, Instant.now());
        return ResponseEntity.status(status).body(response);
    }
}
