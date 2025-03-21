package com.example.chat_service.serivce;

import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatEntity createChat(UUID userId1, UUID userId2) {
        ChatEntity newChat = ChatEntity.builder()
                .id(UUID.randomUUID())
                .userId_1(userId1)
                .userId_2(userId2).
                build();

        chatRepository.save(newChat);
        return newChat;
    }

    public List<ChatEntity> findAllChats() {
        return chatRepository.findAll();
    }

    public ChatEntity findChat(UUID id) {
        return chatRepository.findById(id).orElseThrow(() -> new RuntimeException("pizda"));
    }

}
