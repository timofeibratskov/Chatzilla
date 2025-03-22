package com.example.chat_service.controller;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.dto.ChatRequest;
import com.example.chat_service.serivce.ChatService;
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
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/all")
    public List<ChatDto> findAllChats() {
        return chatService.AllChats();
    }

    @GetMapping("/myChats/{userId}")
    public List<ChatDto> findAllUsersChats(@PathVariable UUID userId) {
        return chatService.findAllChatsByUserId(userId);
    }

    @GetMapping("/{chatId}")
    public ChatDto findChat(@PathVariable UUID chatId) {
        return chatService.findChatById(chatId);
    }

    @PostMapping("/chat/{userId_2}")
    public ChatDto findOrCreatChat(@RequestBody ChatRequest request) {
        return chatService.findOrCreateNewChat(request);
    }

    @DeleteMapping("/{chatId}")
    public void deleteChat(@PathVariable UUID chatId) {
        chatService.dropChat(chatId);
    }
}
