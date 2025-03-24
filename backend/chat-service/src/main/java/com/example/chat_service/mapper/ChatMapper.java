package com.example.chat_service.mapper;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.entity.ChatEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMapper {

    public ChatEntity toEntity(ChatRequest request) {
        return ChatEntity.builder()
                .id(UUID.randomUUID())
                .userId1(request.userId_1())
                .userId2(request.userId_2())
                .build();
    }

    public ChatDto toDto(ChatEntity chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .userId1(chat.getUserId1())
                .userId2(chat.getUserId2())
                .build();
    }

    public ChatEntity toEntity(ChatDto dto) {
        return ChatEntity.builder()
                .userId1(dto.userId1())
                .userId2(dto.userId2())
                .id(dto.id())
                .build();
    }
}
