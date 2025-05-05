package com.example.chat_service.exception.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationError {private String field;
    private String message;
}
