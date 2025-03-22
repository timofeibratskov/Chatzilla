package com.example.chat_service.mapper;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.entity.ChatEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChatMapper {

    public ChatEntity toEntity(ChatDto dto) {
        return ChatEntity.builder()
                .id(dto.id())
                .userId1(dto.userId_1())
                .userId2(dto.userId_2())
                .build();
    }

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
                .userId_1(chat.getUserId1())
                .userId_2(chat.getUserId2())
                .build();
    }

}
