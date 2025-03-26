package com.example.chat_service.serivce;

import com.example.chat_service.client.UserServiceClient;
import com.example.chat_service.dto.*;
import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.mapper.ChatMapper;
import com.example.chat_service.repository.ChatRepository;
import com.example.chat_service.util.JwtUtil;
import com.thoughtworks.xstream.converters.basic.UUIDConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper mapper;
    private final JwtUtil jwtUtil;
    private final UserServiceClient client;

    @Transactional
    public ChatUserPairResponse CreateNewChat(UUID userId,String authHeader) {
        UUID myId = getMyIdFromJwt(authHeader);

        UserDto user = client.getUserById(userId);

        UUID chatId = chatRepository.save(mapper.toEntity(myId, userId)).getId();
        return mapper.toResponse(chatId, user);
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
    public List<ChatUserPairResponse> findMyChats(String authHeader) {
        UUID myId = getMyIdFromJwt(authHeader);
        List<ChatRepository.ChatProjection> chatProjections = chatRepository.findChatPartners(myId);

        List<UUID> partnersIds = chatProjections.stream()
                .map(ChatRepository.ChatProjection::getPartnerId)
                .distinct()
                .collect(Collectors.toList());

        Map<UUID, UserDto> usersMap = client.getUsersBatch(partnersIds)
                .stream()
                .collect(Collectors.toMap(UserDto::id, Function.identity()));

        return chatProjections.stream()
                .map(projection ->
                        new ChatUserPairResponse(
                                projection.getChatId(),
                                usersMap.getOrDefault(
                                        projection.getPartnerId(),
                                        createDeletedUserDto(projection.getPartnerId())
                                )
                        ))
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

    private UserDto createDeletedUserDto(UUID userId) {
        return UserDto.builder()
                .id(userId)
                .username("Deleted User")
                .tag("deleted")
                .build();
    }
}
