package com.example.message_service.dto;

import java.util.UUID;

public record MessageRequest(
        UUID senderId,
        String text
) {
}
