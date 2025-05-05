package com.example.chat_service.controller;

import lombok.RequiredArgsConstructor;
import com.example.chat_service.dto.ChatDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.chat_service.serivce.ChatService;
import com.example.chat_service.dto.ChatUserPairResponse;
import com.example.chat_service.dto.ChatExistenceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Chat API", description = "Управление чатами")
public class ChatController {
    private final ChatService chatService;


    @Operation(summary = "Получить чаты пользователя",
            description = "Возвращает чаты пользователя в которых состоит он")
    @GetMapping("/myChats")
    public List<ChatUserPairResponse> findAllUsersChats(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {

        return chatService.findMyChats(authHeader);
    }

    @Operation(
            summary = "Найти чат по ID",
            description = "Возвращает чат по его уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Чат найден"),
                    @ApiResponse(responseCode = "404", description = "Чат не найден")
            }
    )
    @GetMapping("/{chatId}")
    public ChatDto findChat(@PathVariable UUID chatId) {
        return chatService.findChatById(chatId);
    }

    @Operation(
            summary = " найти чат",
            description = "ищет",
            responses = {
                    @ApiResponse(responseCode = "200", description = "получаем респонз"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
            }
    )
    @GetMapping("/user/{id}")
    public ChatExistenceResponse findChatByUser(@RequestHeader(value = "Authorization", required = false) String authHeader, @PathVariable UUID id) {
        return chatService.checkChatExistence(id, authHeader);
    }

    @Operation(
            summary = "создание  чата с пользователем",
            description = "создает чат ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "отдает чат дто"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
            }
    )
    @PostMapping("/user/{id}")
    public ChatUserPairResponse createChat(
            @RequestHeader(value = "Authorization",
                    required = false)
            String authHeader,
            @PathVariable UUID id) {
        return chatService.createNewChat(id, authHeader);
    }

    @Operation(
            summary = "Удалить чат",
            description = "Удаляет чат по его уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Чат удален"),
                    @ApiResponse(responseCode = "404", description = "Чат не найден")
            }
    )
    @DeleteMapping("/{chatId}")
    public void deleteChat(
            @Parameter(description = "UUID чата", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID chatId
    ) {
        chatService.dropChat(chatId);
    }
}
