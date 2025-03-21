package com.example.user_service.mapper;

import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .id(request.id())
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
}
