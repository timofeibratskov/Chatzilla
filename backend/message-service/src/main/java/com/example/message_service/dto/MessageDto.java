package com.example.message_service.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MessageDto(
        UUID id,
        UUID chatId,
        UUID senderId,
        String text,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
