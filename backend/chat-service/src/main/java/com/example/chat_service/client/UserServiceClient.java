package com.example.chat_service.client;

import com.example.chat_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{id}")
    UserDto getUserById(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/users/batch")
    List<UserDto> getUsersBatch(
            @RequestBody List<UUID> ids,
            @RequestHeader("Authorization") String authorization);
}
