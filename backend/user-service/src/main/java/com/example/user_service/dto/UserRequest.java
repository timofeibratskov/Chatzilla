package com.example.user_service.dto;

public record UserRequest(
        String username,
        String gmail,
        String password,
        String tag
) {
}
