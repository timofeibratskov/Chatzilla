package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

import java.util.UUID;

public record UserRequest(
        UUID id,

        @NotBlank(message = "Username обязателен!")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email должен быть валидным")
        String gmail,

        @NotBlank(message = "Password обязателен!")
        String password,

        @Nullable
        String tag
) {
}