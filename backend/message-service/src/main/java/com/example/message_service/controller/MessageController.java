package com.example.message_service.controller;

import com.example.message_service.dto.MessageDto;
import com.example.message_service.dto.MessageRequest;
import com.example.message_service.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Message API", description = "Управление сообщениями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Operation(
            summary = "Получить все сообщения",
            description = "Возвращает список всех сообщений в системе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение списка сообщений",
                            content = @Content(schema = @Schema(implementation = MessageDto.class))
                    )
            }
    )
    @GetMapping("/all")
    public List<MessageDto> findAllMessages() {
        return messageService.findAllMessages();
    }

    @Operation(
            summary = "Найти сообщение по ID",
            description = "Возвращает сообщение по его уникальному идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сообщение найдено"),
                    @ApiResponse(responseCode = "404", description = "Сообщение не найдено")
            }
    )
    @GetMapping("/{messageId}")
    public MessageDto findMessage(
            @Parameter(description = "UUID сообщения", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID messageId
    ) {
        return messageService.findMessage(messageId);
    }

    @Operation(
            summary = "Получить сообщения чата",
            description = "Возвращает все сообщения в указанном чате",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщения успешно получены",
                            content = @Content(schema = @Schema(implementation = MessageDto.class))
                    )
            }
    )
    @GetMapping("/chats/{chatId}")
    public List<MessageDto> findAllMessagesInChat(
            @Parameter(description = "UUID чата", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID chatId
    ) {
        return messageService.findAllMessagesInChat(chatId);
    }

    @Operation(
            summary = "Отправить сообщение",
            description = "Создает новое сообщение в указанном чате",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сообщение успешно создано"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос")
            }
    )
    @PostMapping("/{chatId}")
    public void sendMessage(
            @Parameter(description = "UUID чата", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID chatId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные сообщения",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MessageRequest.class))
            )
            @RequestBody MessageRequest request
    ) {
        messageService.createMessage(chatId, request);
    }

    @Operation(
            summary = "Обновить сообщение",
            description = "Обновляет содержимое существующего сообщения",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сообщение обновлено"),
                    @ApiResponse(responseCode = "404", description = "Сообщение не найдено")
            }
    )
    @PutMapping("/{chatId}")
    public void updateMessage(
            @Parameter(description = "UUID чата", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID chatId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные сообщения",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MessageRequest.class))
            )
            @RequestBody MessageRequest request
    ) {
        messageService.updateMessage(chatId, request);
    }

    @Operation(
            summary = "Удалить сообщение",
            description = "Удаляет сообщение по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сообщение удалено"),
                    @ApiResponse(responseCode = "404", description = "Сообщение не найдено")
            }
    )
    @DeleteMapping("/{messageId}")
    public void deleteMessage(
            @Parameter(description = "UUID сообщения", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID messageId
    ) {
        messageService.dropMessage(messageId);
    }
}