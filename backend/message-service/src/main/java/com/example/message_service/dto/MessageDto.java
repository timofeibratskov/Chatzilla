package com.example.message_service.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Сущность сообщения")
public record MessageDto(
        @Schema(description = "UUID сообщения", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "UUID чата", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID chatId,

        @Schema(description = "Текст сообщения", example = "Привет, мир!")
        String text,

        @Schema(description = "UUID отправителя", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID senderId,

        @Schema(description = "Время создания", example = "2025-03-22T15:30:00")
        LocalDateTime createdAt,

        @Schema(description = "Время последнего обновления", example = "2025-03-22T15:31:00")
        LocalDateTime updatedAt
) {
}