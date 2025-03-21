package com.example.chat_service.serivce;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.mapper.ChatMapper;
import com.example.chat_service.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    public ChatEntity findOrCreateNewChat(UUID userId_1, UUID userId_2) {

        return chatRepository
                .findChatByUserIds(userId_1,userId_2)
                .orElseGet(() -> {
                    ChatEntity newChat = ChatEntity.builder()
                            .id(UUID.randomUUID())
                            .userId1(userId_1)
                            .userId2(userId_2)
                            .build();

                    return chatRepository.save(newChat);
                });
    }

    @Transactional(readOnly = true)
    public List<ChatEntity> findAllChats() {
        return chatRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ChatEntity> findAllChatsByUserId(UUID userId) {
        return chatRepository.findByUserId1OrUserId2(userId,userId);
    }

    @Transactional(readOnly = true)
    public ChatEntity findChatById(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(()
                        -> new EntityNotFoundException("чата с таким id:" + chatId + " не существует!"));
    }

    @Transactional
    public void dropChat(UUID chatId) {
        ChatEntity chatEntity = findChatById(chatId);
        chatRepository.delete(chatEntity);
    }
}
