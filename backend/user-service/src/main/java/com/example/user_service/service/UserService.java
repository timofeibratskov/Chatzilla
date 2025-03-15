package com.example.user_service.service;

import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserLoginRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.exception.InvalidCredentialsException;
import com.example.user_service.exception.ResourceAlreadyExistsException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public UserDto registerUser(UserRequest request) {
        // Проверяем уникальность тэга и почты
        checkUniqueTag(request.tag(), null); // userId = null, так как это регистрация
        checkUniqueGmail(request.gmail(), null);

        // Создаем нового пользователя
        UserEntity entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findUserByTag(String tag) {
        UserEntity entity = repository.findByTag(tag)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with tag: %s not found", tag)));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto findUserById(UUID id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id: %s not found", id)));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto loginUser(UserLoginRequest request) {
        // Находим пользователя по почте
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email: %s not found", request.gmail())));

        // Проверяем пароль
        if (!entity.getPassword().equals(request.password())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return mapper.toDto(entity);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserUpdateRequest request) {
        // Находим пользователя по ID
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Проверяем уникальность тэга (если он изменен)
        if (request.tag() != null && !request.tag().equals(entity.getTag())) {
            checkUniqueTag(request.tag(), id); // userId = id, чтобы исключить текущего пользователя
            entity.setTag(request.tag());
        }

        // Обновляем имя (если передано)
        if (request.username() != null) {
            entity.setUsername(request.username());
        }

        // Обновляем пароль (если передан)
        if (request.password() != null) {
            entity.setPassword(request.password()); // Пока без шифрования
        }

        // Сохраняем изменения
        UserEntity updatedUser = repository.save(entity);
        return mapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(UserLoginRequest request) {
        // Находим пользователя по почте
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email: %s not found", request.gmail())));

        // Проверяем пароль
        if (!entity.getPassword().equals(request.password())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        // Удаляем пользователя
        repository.delete(entity);
    }

    private void checkUniqueTag(String tag, UUID userId) {
        repository.findByTag(tag).ifPresent(user -> {
            if (userId == null || !user.getId().equals(userId)) {
                throw new ResourceAlreadyExistsException(
                        String.format("This tag: %s already exists", tag));
            }
        });
    }

    private void checkUniqueGmail(String gmail, UUID userId) {
        repository.findByGmail(gmail).ifPresent(user -> {
            if (userId == null || !user.getId().equals(userId)) {
                throw new ResourceAlreadyExistsException(
                        String.format("This gmail: %s already exists", gmail));
            }
        });
    }
}