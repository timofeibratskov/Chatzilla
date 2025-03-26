package com.example.chat_service.client;

import com.example.chat_service.dto.UserDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8080/api/v1/users")
public interface UserServiceClient {
    @GetMapping("/{id}")
    UserDto getUserById(
            @PathVariable UUID id);

    @PostMapping("/batch")
    List<UserDto> getUsersBatch(
            @RequestBody List<UUID> ids
    );
}
