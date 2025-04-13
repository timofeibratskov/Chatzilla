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
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.nio.channels.AcceptPendingException;
import java.rmi.AccessException;
import java.security.Principal;
import java.time.LocalDateTime;
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
        return messageService.createMessage(chatId,request,userId);
    }

    // @MessageMapping("/chat/{chatId}/message/{messageId}/update")
    // @SendTo("/topic/chat/{chatId}/update")
    // public MessageResponse updateMessage(@DestinationVariable UUID chatId,
    //                                      @DestinationVariable UUID messageId,
    //                                      MessageRequest request) {
    //     System.out.println("Updating message " + messageId + " in chat " + chatId);
    //     return messageService.updateMessage(messageId, request);
    // }

    // @MessageMapping("/chat/{chatId}/message/{messageId}/delete")
    // @SendTo("/topic/chat/{chatId}/delete")
    // public UUID deleteMessage(@DestinationVariable UUID chatId,
    //                           @DestinationVariable UUID messageId) {
    //     System.out.println("Delete message " + messageId + " in chat " + chatId);
    //     return messageService.dropMessage(messageId);
    // }
}
