package com.example.message_service.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MessageResponse(
        UUID id,
        UUID senderId,
        String message,
        LocalDateTime date
) {
}
