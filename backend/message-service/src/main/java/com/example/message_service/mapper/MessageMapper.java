package com.example.message_service.mapper;

import com.example.message_service.dto.MessageDto;
import com.example.message_service.dto.MessageRequest;
import com.example.message_service.dto.MessageResponse;
import com.example.message_service.entity.MessageEntity;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MessageMapper {
    public MessageEntity toEntity(MessageRequest request, UUID chatId, String userId) {
        return MessageEntity.builder()
                .id(UUID.randomUUID())
                .chatId(chatId)
                .text(request.text())
                .senderId(UUID.fromString(userId))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MessageEntity toEntity(MessageDto dto) {
        return MessageEntity.builder()
                .id(dto.id())
                .senderId(dto.senderId())
                .chatId(dto.chatId())
                .text(dto.text())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }

    public MessageDto toDto(MessageEntity entity) {
        return MessageDto.builder()
                .id(entity.getId())
                .chatId(entity.getChatId())
                .createdAt(entity.getCreatedAt())
                .senderId(entity.getSenderId())
                .updatedAt(entity.getUpdatedAt())
                .text(entity.getText())
                .build();
    }
    public MessageResponse toResponse(MessageEntity message){
        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .message(message.getText())
                .date(LocalDateTime.now())
                .build();
    }
}
