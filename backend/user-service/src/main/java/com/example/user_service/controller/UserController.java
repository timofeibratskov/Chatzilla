package com.example.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.user_service.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserDtoResponse;
import com.example.user_service.service.UserService;
import com.example.user_service.dto.UserLoginRequest;
import com.example.user_service.dto.UserUpdateRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Операции с пользователями")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService service;

    @Operation(summary = "Регистрация нового пользователя", description = "Создает нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping("/register")
    public UserDtoResponse register(@RequestBody @Valid UserRequest request) {
        return service.registerUser(request);
    }

    @Operation(summary = "Логин пользователя", description = "Аутентификация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping("/login")
    public UserDtoResponse login(@RequestBody @Valid UserLoginRequest request) {
        return service.loginUser(request);
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по его уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public UserDto getUserById(
            @Parameter(description = "Уникальный идентификатор пользователя", required = true)
            @PathVariable UUID id) {
        return service.findUserById(id);
    }

    @Operation(summary = "Получить пользователей по тэгу", description = "Возвращает пользователей по его уникальному тэгу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/tag/{tag}")
    public List<UserDtoResponse> getUserByTag(
            @Parameter(description = "Уникальный тэг пользователя", required = true)
            @PathVariable String tag) {
        return service.findAllSimilarUsersByTags(tag);
    }

    @Operation(summary = "Обновить данные пользователя", description = "Обновляет информацию о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PutMapping("/{id}")
    public void updateUser(
            @Parameter(description = "Уникальный идентификатор пользователя", required = true)
            @PathVariable UUID id,
            @RequestBody UserUpdateRequest request) {
        service.updateUser(id, request);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его учетным данным")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody @Valid UserLoginRequest request) {
        service.deleteUser(request);
    }

    @PostMapping("/batch")
    @Operation(summary = "Получить пользователей по списку ID", description = "это только для коммуникации с chat сервисом")
    public List<UserDtoResponse> getUsersBatch(@RequestBody List<UUID> ids) {
        return service.findUsersByIds(ids);
    }

}