package com.example.chat_service.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ChatUserPairResponse(
        UUID chatId,
        UserDto user
) {
}
