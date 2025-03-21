package com.example.chat_service.controller;

import com.example.chat_service.dto.ChatDto;
import com.example.chat_service.entity.ChatEntity;
import com.example.chat_service.serivce.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/all")
    public List<ChatEntity> findAllChats() {
        return chatService.findAllChats();
    }

    @GetMapping("/myChats/{userId}")
    public List<ChatEntity> findAllUsersChats(@PathVariable UUID userId) {
        return chatService.findAllChatsByUserId(userId);
    }

    @GetMapping("/{chatId}")
    public ChatEntity findChat(@PathVariable UUID chatId) {
        return chatService.findChatById(chatId);
    }

    @PostMapping("/chat/{userId_2}")
    public ChatEntity findOrCreatChat(@RequestParam UUID userId_1,@PathVariable UUID userId_2) {
        return chatService.findOrCreateNewChat(userId_1,userId_2);
    }

    @DeleteMapping("/{chatId}")
    public void deleteChat(@PathVariable UUID chatId) {
        chatService.dropChat(chatId);
    }
}
