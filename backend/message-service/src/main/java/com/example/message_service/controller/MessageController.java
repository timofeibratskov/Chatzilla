package com.example.message_service.controller;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.message_service.dto.MessageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import com.example.message_service.dto.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.message_service.service.MessageService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Message API", description = "Управление сообщениями")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

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
    public List<MessageResponse> findAllMessagesInChat(
            @Parameter(description = "UUID чата", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID chatId
    ) {
        return messageService.findAllMessagesInChat(chatId);
    }

}