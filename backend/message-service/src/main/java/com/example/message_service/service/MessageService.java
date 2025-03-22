package com.example.message_service.service;

import com.example.message_service.dto.MessageDto;
import com.example.message_service.dto.MessageRequest;
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
    public void createMessage(UUID chatId, MessageRequest request) {
        MessageEntity message = messageMapper.toEntity(request, chatId);
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findAllMessagesInChat(UUID chatId) {
        return messageRepository.findAllByChatId(chatId)
                .stream()
                .map(messageMapper::toDto)
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
    public void updateMessage(UUID messageId, MessageRequest request) {
        MessageEntity message = messageMapper.toEntity(findMessage(messageId));
        System.out.println(request.toString());
        if (!Objects.equals(request.text(), message.getText())) {
            System.out.println(message.toString());
            message.setText(request.text());
            System.out.println(message.toString());
            message.setUpdatedAt(LocalDateTime.now());
            System.out.println(message.toString());
            messageRepository.save(message);
        }
    }

    @Transactional
    public void dropMessage(UUID messageId) {
        MessageDto message = findMessage(messageId);
        messageRepository.delete(messageMapper.toEntity(message));
    }
}
