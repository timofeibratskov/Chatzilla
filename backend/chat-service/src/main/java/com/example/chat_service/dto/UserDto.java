package com.example.chat_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String username,
        String tag
) {
}
