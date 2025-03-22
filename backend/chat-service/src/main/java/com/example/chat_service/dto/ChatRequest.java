package com.example.chat_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(name = "ChatRequest", description = "Запрос для создания/поиска чата")
public record ChatRequest(
        @Schema(description = "UUID первого пользователя",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID userId_1,

        @Schema(description = "UUID второго пользователя",
                example = "123e4567-e89b-12d3-a456-426614174000")
        UUID userId_2
) {
}