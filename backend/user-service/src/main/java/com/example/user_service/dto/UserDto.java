package com.example.user_service.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record UserDto(
        UUID id,
        String username,
        String gmail,
        String password,
        String tag
) {
}
