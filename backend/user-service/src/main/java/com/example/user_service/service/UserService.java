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

        if (repository.findByGmail(request.gmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Почта " + request.gmail() + " уже существует!");
        }

        String tag = request.tag();
        if (tag == null || tag.isEmpty()) {
            tag = "user" + System.currentTimeMillis();
            System.out.println("....тэг добавили"+ tag);
        } else {
            if (repository.findByTag(tag).isPresent()) {
                throw new ResourceAlreadyExistsException("Тег " + tag + " уже существует!");
            }
        }
        UserEntity entity = mapper.toEntity(request);
        entity.setTag(tag);

        System.out.println(entity.toString());

        UserEntity savedEntity = repository.save(entity);

        return mapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public UserDto findUserById(UUID id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с id: %s не найден", id)));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto loginUser(UserLoginRequest request) {
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с почтой: %s не найден", request.gmail())));

        if (!entity.getPassword().equals(request.password())) {
            throw new InvalidCredentialsException("Неверный пароль");
        }

        return mapper.toDto(entity);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserUpdateRequest request) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id: " + id + " не найден"));

        if (request.tag() != null && !request.tag().equals(entity.getTag())) {
            if (repository.findByTag(request.tag()).isPresent()) {
                throw new ResourceAlreadyExistsException("Тег " + request.tag() + " уже существует!");
            }
            entity.setTag(request.tag());
        }

        if (request.username() != null) {
            entity.setUsername(request.username());
        }

        if (request.password() != null) {
            entity.setPassword(request.password());
        }

        UserEntity updatedUser = repository.save(entity);
        return mapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(UserLoginRequest request) {
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с почтой: %s не найден", request.gmail())));

        if (!entity.getPassword().equals(request.password())) {
            throw new InvalidCredentialsException("Неверный пароль");
        }

        repository.delete(entity);
    }

    public List<UserDto> findAllSimilarUsersByTags(String tag) {
        List<UserEntity> entities = repository.findByTagContaining(tag);
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}