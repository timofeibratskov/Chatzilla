package com.example.user_service.dto;

public record UserLoginRequest(
        String gmail,
        String password
) {
}
