package com.example.message_service.dto;

import java.util.UUID;

public record UpdateMessageRequest(
        UUID senderId,
        String updatedText
) {
}
