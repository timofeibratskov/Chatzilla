package com.example.user_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDtoResponse(
        UUID id,
        String username,
        String tag,
        String token
) {
}
