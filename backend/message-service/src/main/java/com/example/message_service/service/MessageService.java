package com.example.message_service.service;

import com.example.message_service.dto.MessageDto;
import com.example.message_service.dto.MessageRequest;
import com.example.message_service.dto.MessageResponse;
import com.example.message_service.entity.MessageEntity;
import com.example.message_service.mapper.MessageMapper;
import com.example.message_service.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Transactional
    public MessageResponse createMessage(UUID chatId, MessageRequest request,String userId) {
        MessageEntity message = messageMapper.toEntity(request, chatId,userId);
        messageRepository.save(message);
        return messageMapper.toResponse(message);
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> findAllMessagesInChat(UUID chatId) {
        return messageRepository.findAllByChatId(chatId)
                .stream()
                .map(messageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MessageDto findMessage(UUID messageId) {
        MessageEntity entity = messageRepository.findById(messageId).orElseThrow(()
                -> new EntityNotFoundException("такого сообщения не сущестует")

        );
        return messageMapper.toDto(entity);
    }

    @Transactional
    public MessageResponse updateMessage(UUID messageId, MessageRequest request) {
        MessageEntity message = messageMapper.toEntity(findMessage(messageId));
        if (!Objects.equals(request.text(), message.getText())) {
            message.setText(request.text());
            message.setUpdatedAt(LocalDateTime.now());
            messageRepository.save(message);
        }
        return messageMapper.toResponse(message);

    }

    @Transactional
    public UUID dropMessage(UUID messageId) {
        MessageDto message = findMessage(messageId);
        messageRepository.delete(messageMapper.toEntity(message));
        return messageId;
    }
}
