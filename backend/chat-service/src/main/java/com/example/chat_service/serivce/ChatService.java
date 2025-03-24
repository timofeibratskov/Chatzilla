package com.example.chat_service.serivce;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatExistenceResponse;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.mapper.ChatMapper;
import com.example.chat_service.repository.ChatRepository;
import com.example.chat_service.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public ChatDto CreateNewChat(UUID otherUserId, String authHeader) {
        UUID myId = getMyIdFromJwt(authHeader);

        ChatEntity newChat = ChatEntity.builder()
                .id(UUID.randomUUID())
                .userId1(myId)
                .userId2(otherUserId)
                .build();

        chatRepository.save(newChat);
        return mapper.toDto(newChat);
    }

    @Transactional
    public ChatExistenceResponse checkChatExistence(UUID id, String authHeader) {
        UUID myId = getMyIdFromJwt(authHeader);
        return chatRepository.findChatByUserIds(id, myId)
                .map(chatEntity ->
                        new ChatExistenceResponse(
                                chatEntity.getId(),
                                true))
                .orElse(new ChatExistenceResponse(
                        null,
                        false));
    }

    @Transactional(readOnly = true)
    public List<ChatDto> findMyChats(String authHeader) {
        UUID myId = getMyIdFromJwt(authHeader);

        return chatRepository.findByUserId1OrUserId2(myId, myId)
                .stream()
                .map(chat -> mapper.toDto(chat))
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

    private UUID getMyIdFromJwt(String authHeader) {
        String token = authHeader.substring(7);
        return UUID.fromString(jwtUtil.getUserIdFromToken(token));
    }
}
