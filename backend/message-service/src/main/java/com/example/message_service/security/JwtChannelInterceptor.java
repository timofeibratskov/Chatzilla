package com.example.message_service.security;

import com.example.message_service.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        String tokenWithBearer = accessor.getFirstNativeHeader("Authorization");

        System.out.println("Received STOMP command: " + command);
        System.out.println("Authorization header: " + tokenWithBearer);

        if (StompCommand.CONNECT.equals(command) || StompCommand.SEND.equals(command)) {
            if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
                String token = tokenWithBearer.substring(7);
                try {
                    String userId = jwtUtil.getUserIdFromToken(token);
                    System.out.println("Extracted userId: " + userId);
                    Authentication auth = new UsernamePasswordAuthenticationToken(userId, null);
                    accessor.setUser(auth);
                    accessor.getSessionAttributes().put("userId", userId);
                    System.out.println("Authentication set for user: " + userId + ", Principal: " + accessor.getUser());
                } catch (Exception e) {
                    System.err.println("Error validating JWT: " + e.getMessage());
                    throw new MessagingException("Invalid JWT");
                }
            } else if (StompCommand.SEND.equals(command)) {
                System.err.println("Missing or invalid JWT for SEND command");
                throw new MessagingException("Missing or invalid JWT for sending message");
            }
        }
        return message;
    }
}