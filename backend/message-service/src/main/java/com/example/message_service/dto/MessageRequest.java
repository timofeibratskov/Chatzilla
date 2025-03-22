package com.example.message_service.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос для создания/обновления сообщения")
public record MessageRequest(
        @Schema(description = "Текст сообщения", example = "Привет, мир!")
        String text,

        @Schema(description = "UUID отправителя", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID senderId
) {
}
