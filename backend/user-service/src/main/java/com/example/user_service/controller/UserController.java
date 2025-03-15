package com.example.user_service.controller;

import lombok.RequiredArgsConstructor;
import com.example.user_service.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.service.UserService;
import com.example.user_service.dto.UserLoginRequest;
import com.example.user_service.dto.UserUpdateRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;
//http://localhost:8080/swagger-ui/index.html#/
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Операции с пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @Operation(summary = "Получить всех пользователей")
    @GetMapping("/all")
    public List<UserDto> getAll() {
        return service.findAllUsers();
    }

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public UserDto register(@RequestBody UserRequest request) {
        return service.registerUser(request);
    }

    @Operation(summary = "Логин пользователя")
    @PostMapping("/login")
    public UserDto login(@RequestBody UserLoginRequest request) {
        return service.loginUser(request);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        return service.findUserById(id);
    }

    @Operation(summary = "Получить пользователя по тэгу")
    @GetMapping("/tag/{tag}")
    public UserDto getUserByTag(@PathVariable String tag) {
        return service.findUserByTag(tag);
    }

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequest request) {
        return service.updateUser(id, request);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserLoginRequest request) {
        service.deleteUser(request);
    }
}