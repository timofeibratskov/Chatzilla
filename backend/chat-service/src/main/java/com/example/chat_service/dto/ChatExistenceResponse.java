package com.example.chat_service.dto;

import java.util.UUID;

public record ChatExistenceResponse(
        UUID chatId,
        Boolean exists
) {
}
