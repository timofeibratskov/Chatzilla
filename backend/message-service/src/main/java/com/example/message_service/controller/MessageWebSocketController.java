package com.example.message_service.controller;

import com.example.message_service.dto.MessageRequest;
import com.example.message_service.dto.MessageResponse;
import com.example.message_service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.rmi.AccessException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat/{chatId}/message")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse handleMessage(
            @DestinationVariable UUID chatId,
            @Payload MessageRequest request,
            SimpMessageHeaderAccessor accessor) throws AccessException {
        String userId = (String) accessor.getSessionAttributes().get("userId");
        if (userId == null) {
            throw new AccessException("problema with Id");
        }
        return messageService.createMessage(chatId, request, userId);
    }

    @MessageMapping("/chat/{chatId}/message/{messageId}/update")
    @SendTo("/topic/chat/{chatId}/update")
    public MessageResponse updateMessage(@DestinationVariable UUID chatId,
                                         @DestinationVariable UUID messageId,
                                         @Payload MessageRequest request,
                                         SimpMessageHeaderAccessor accessor) throws AccessException {

        String userId = (String) accessor.getSessionAttributes().get("userId");
        if (userId == null) {
            throw new AccessException("problema with Id");
        }
        return messageService.updateMessage(messageId, request, userId);
    }

    @MessageMapping("/chat/{chatId}/message/{messageId}/delete")
    @SendTo("/topic/chat/{chatId}/delete")
    public UUID deleteMessage(@DestinationVariable UUID chatId,
                              @DestinationVariable UUID messageId,
                              SimpMessageHeaderAccessor accessor) throws AccessException {
        String userId = (String) accessor.getSessionAttributes().get("userId");
        if (userId == null) {
            throw new AccessException("problema with Id");
        }
        return messageService.dropMessage(messageId, userId);
    }
}
