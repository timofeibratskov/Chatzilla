package com.example.user_service.dto;

public record UserUpdateRequest(
        String username,
        String password,
        String tag
) {

}
