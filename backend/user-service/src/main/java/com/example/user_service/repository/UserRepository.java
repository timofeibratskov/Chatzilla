package com.example.user_service.repository;

import com.example.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByGmail(String gmail);

    Optional<UserEntity> findByTag(String tag);

    List<UserEntity> findByTagContaining(String tag);
}
