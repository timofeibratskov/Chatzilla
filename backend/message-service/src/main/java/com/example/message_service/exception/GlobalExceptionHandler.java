package com.example.message_service.exception;

import com.example.message_service.exception.models.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MessageOwnershipException.class)
    public ResponseEntity<ErrorResponse> handleOwnershipViolation(MessageOwnershipException ex) {
        ErrorResponse error = new ErrorResponse(
                "OWNERSHIP_VIOLATION",  // Стандартный код ошибки
                ex.getMessage()         // Детали из исключения
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

}
