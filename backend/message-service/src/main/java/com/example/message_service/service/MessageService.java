package com.example.message_service.service;

import com.example.message_service.entity.MessageEntity;
import com.example.message_service.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<MessageEntity> findAllMessages() {
        return messageRepository.findAll();
    }

    public List<MessageEntity> findAllMessagesInChat(UUID chatId) {
        return messageRepository.findAllByChatId(chatId);
    }

    public void createMessage(MessageEntity message) {
        messageRepository.save(message);
    }

    public MessageEntity findMessage(UUID messageId) {
        MessageEntity entity = messageRepository.findById(messageId).orElseThrow(() -> new EntityNotFoundException("no found"));
        return entity;
    }

    //public void updateMessage(MessageEntity message){}

    public void dropMessage(UUID messageId) {
        messageRepository.delete(findMessage(messageId));
    }
}
