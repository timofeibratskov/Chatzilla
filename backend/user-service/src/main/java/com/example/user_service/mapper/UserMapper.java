package com.example.user_service.mapper;

import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserDtoResponse;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .username(request.username())
                .gmail(request.gmail())
                .password(request.password())
                .tag(request.tag())
                .build();
    }

    public UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .gmail(entity.getGmail())
                .password(entity.getPassword())
                .tag(entity.getTag())
                .build();
    }

    public UserDtoResponse toResponse(UserEntity entity) {
        return UserDtoResponse.builder()
                .id(entity.getId())
                .tag(entity.getTag())
                .username(entity.getUsername())
                .build();
    }

    public UserDtoResponse toResponse(UserEntity entity, String token) {
        return UserDtoResponse.builder()
                .id(entity.getId())
                .tag(entity.getTag())
                .username(entity.getUsername())
                .token(token)
                .build();
    }
}
