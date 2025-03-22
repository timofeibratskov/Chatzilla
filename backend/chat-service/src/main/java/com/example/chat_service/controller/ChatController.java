package com.example.chat_service.controller;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.serivce.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@Tag(name = "Chat API", description = "Управление чатами")
public class ChatController {
    private final ChatService chatService;

    @Operation(
            summary = "Получить все чаты",
            description = "Возвращает список всех существующих чатов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный запрос",
                            content = @Content(schema = @Schema(implementation = ChatDto.class))
                    )
            }
    )
    @GetMapping("/all")
    public List<ChatDto> findAllChats() {
        return chatService.AllChats();
    }

    @Operation(
            summary = "Получить чаты пользователя",
            description = "Возвращает все чаты, связанные с указанным пользователем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный запрос",
                            content = @Content(schema = @Schema(implementation = ChatDto.class))

                    )
            }
    )
    @GetMapping("/myChats/{userId}")
    public List<ChatDto> findAllUsersChats(@PathVariable UUID userId) {
        return chatService.findAllChatsByUserId(userId);
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
            summary = "Создать или найти чат",
            description = "Создает новый чат или возвращает существующий между двумя пользователями",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Чат создан/найден"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
            }
    )
    @PostMapping("/chat")
    public ChatDto findOrCreatChat(@RequestBody ChatRequest request) {
        return chatService.findOrCreateNewChat(request);
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
