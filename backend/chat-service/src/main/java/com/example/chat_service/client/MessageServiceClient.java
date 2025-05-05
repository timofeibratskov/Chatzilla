package com.example.chat_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "message-service")
public interface MessageServiceClient {

    @DeleteMapping("/api/messages/chats/{chatId}")
    void deleteAllMessagesInChat(
            @PathVariable UUID chatId
    );
}
