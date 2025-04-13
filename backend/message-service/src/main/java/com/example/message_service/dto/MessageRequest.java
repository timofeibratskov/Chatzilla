package com.example.message_service.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос для создания/обновления сообщения")
public record MessageRequest(
        @Schema(description = "Текст сообщения", example = "Привет, мир!")
        String text
) {
}
