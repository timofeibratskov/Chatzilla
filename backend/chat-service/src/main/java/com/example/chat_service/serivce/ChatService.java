package com.example.chat_service.serivce;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.mapper.ChatMapper;
import com.example.chat_service.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper mapper;
    private final ChatMapper chatMapper;

    @Transactional
    public ChatDto findOrCreateNewChat(ChatRequest request) {
        ChatEntity chat = chatRepository
                .findChatByUserIds(request.userId_1(), request.userId_2())
                .orElseGet(() -> {
                    ChatEntity newChat = mapper.toEntity(request);
                    return chatRepository.save(newChat);
                });
        return mapper.toDto(chat);
    }

    @Transactional(readOnly = true)
    public List<ChatDto> AllChats() {
        return chatRepository.findAll()
                .stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatDto> findAllChatsByUserId(UUID userId) {
        return chatRepository.findByUserId1OrUserId2(userId, userId)
                .stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChatDto findChatById(UUID chatId) {
        ChatEntity entity = chatRepository.findById(chatId)
                .orElseThrow(()
                        -> new EntityNotFoundException("чата с таким id:" + chatId + " не существует!"));
        return mapper.toDto(entity);
    }

    @Transactional
    public void dropChat(UUID chatId) {
        ChatDto chat = findChatById(chatId);
        chatRepository.delete(mapper.toEntity(chat));
    }
}
