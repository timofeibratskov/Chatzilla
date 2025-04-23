package com.example.chat_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final String secretKey = "T5Rsd+2zSxyfWBXpDqmaZG3qULKHANv7pD5VtdgvDz6BXS4y0YVRFeo4zVx8vWbNvrAQ6aCXGUQII/ZosAltbA==";

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("user_id", String.class);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
