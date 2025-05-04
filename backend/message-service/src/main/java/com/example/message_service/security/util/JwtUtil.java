package com.example.message_service.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final String SECRET_KEY = "T5Rsd+2zSxyfWBXpDqmaZG3qULKHANv7pD5VtdgvDz6BXS4y0YVRFeo4zVx8vWbNvrAQ6aCXGUQII/ZosAltbA==";
    private static final String USER_ID_CLAIM = "user_id";
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get(USER_ID_CLAIM, String.class);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
