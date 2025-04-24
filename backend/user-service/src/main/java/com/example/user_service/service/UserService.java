package com.example.user_service.service;

import lombok.RequiredArgsConstructor;
import com.example.user_service.dto.UserDto;
import org.springframework.stereotype.Service;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.security.JwtUtil;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import com.example.user_service.dto.UserDtoResponse;
import com.example.user_service.dto.UserLoginRequest;
import com.example.user_service.dto.UserUpdateRequest;
import com.example.user_service.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.user_service.exception.InvalidCredentialsException;
import com.example.user_service.exception.ResourceAlreadyExistsException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDtoResponse registerUser(UserRequest request) {
        if (repository.findByGmail(request.gmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Почта " + request.gmail() + " уже существует!");
        }
        String tag = request.tag();
        if (tag == null || tag.isEmpty()) {
            tag = "user" + System.currentTimeMillis();
        } else {
            if (repository.findByTag(tag).isPresent()) {
                throw new ResourceAlreadyExistsException("Тег " + tag + " уже существует!");
            }
        }
        UserEntity entity = mapper.toEntity(request);
        entity.setTag(tag);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        repository.save(entity);
        return mapper.toResponse(entity, jwtUtil.generateToken(entity.getId()));
    }

    @Transactional(readOnly = true)
    public UserDto findUserById(UUID id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с id: %s не найден", id)));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDtoResponse loginUser(UserLoginRequest request) {
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с почтой: %s не найден", request.gmail())));
        if (!passwordEncoder.matches(request.password(), entity.getPassword())) {
            throw new InvalidCredentialsException("Неверный пароль");
        }
        return mapper.toResponse(entity, jwtUtil.generateToken(entity.getId()));
    }

    @Transactional
    public void updateUser(UUID id, UserUpdateRequest request) {
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
            entity.setPassword(passwordEncoder.encode(request.password()));
        }
        repository.save(entity);
    }

    @Transactional
    public void deleteUser(UserLoginRequest request) {
        UserEntity entity = repository.findByGmail(request.gmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с почтой: %s не найден", request.gmail())));
        if (!passwordEncoder.matches(request.password(), entity.getPassword())) {
            throw new InvalidCredentialsException("Неверный пароль");
        }
        repository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> findUsersByIds(List<UUID> ids) {
        return repository.findAllByIdIn(ids)
                .stream().map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> findAllSimilarUsersByTags(String tag) {
        List<UserEntity> entities = repository.findByTagContaining(tag);
        return entities.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}