package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UserLoginRequest(
        @NotBlank(message = "почта должна быть!")
        @Email(message = "почта должна быть валидна!")
        String gmail,

        @NotBlank(message = "пароль должен быть!")
        @NonNull
        String password
) {
}
