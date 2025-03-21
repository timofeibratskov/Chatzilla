package com.example.chat_service.controller;

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

    @GetMapping("/{id}")
    public ChatEntity findChat(@PathVariable UUID id) {
        return chatService.findChat(id);
    }

    @PostMapping("/chat/{userId_2}")
    public ChatEntity createNewChat(@RequestParam UUID userId_1, @PathVariable UUID userId_2) {
        return chatService.createChat(userId_1, userId_2);
    }

}
