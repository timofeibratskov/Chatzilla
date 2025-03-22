package com.example.message_service.controller;

import com.example.message_service.entity.MessageEntity;
import com.example.message_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/all")
    public List<MessageEntity> findAllMessages() {
        return messageService.findAllMessages();
    }

    @GetMapping("/{chatId}")
    public List<MessageEntity> findAllMessagesInChat(@PathVariable UUID chatId) {
        return messageService.findAllMessagesInChat(chatId);
    }

    @PostMapping()
    public void sendMessage(@RequestBody MessageEntity entity) {
        messageService.createMessage(entity);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.dropMessage(messageId);
    }
}
