package com.example.message_service.controller;

import com.example.message_service.dto.MessageDto;
import com.example.message_service.dto.MessageRequest;
import com.example.message_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/all")
    public List<MessageDto> findAllMessages() {
        return messageService.findAllMessages();
    }

    @GetMapping("/{messageId}")
    public MessageDto findMessage(@PathVariable UUID messageId) {
        return messageService.findMessage(messageId);
    }

    @GetMapping("/chats/{chatId}")
    public List<MessageDto> findAllMessagesInChat(@PathVariable UUID chatId) {
        return messageService.findAllMessagesInChat(chatId);
    }

    @PostMapping("/{chatId}")
    public void sendMessage(
            @PathVariable UUID chatId,
            @RequestBody MessageRequest request) {
        messageService.createMessage(chatId, request);
    }

    @PutMapping("/{chatId}")
    public void updateMessage(
            @PathVariable UUID chatId,
            @RequestBody MessageRequest request) {
        messageService.updateMessage(chatId, request);
    }

    @DeleteMapping("/{messageId}")

    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.dropMessage(messageId);
    }
}
