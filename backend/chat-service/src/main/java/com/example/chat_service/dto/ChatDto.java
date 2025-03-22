package com.example.chat_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatDto(
        UUID id,

        UUID userId_1,

        UUID userId_2
) {
}
