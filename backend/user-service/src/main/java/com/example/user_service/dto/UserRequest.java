package com.example.user_service.dto;

import java.util.UUID;

public record UserRequest(
        UUID id,
        String username,
        String gmail,
        String password,
        String tag
) {
}
