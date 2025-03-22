package com.example.chat_service.repository;

import com.example.chat_service.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {

    @Query("SELECT c FROM ChatEntity c WHERE " +
            "(c.userId1 = :userId1 AND c.userId2 = :userId2) OR " +
            "(c.userId1 = :userId2 AND c.userId2 = :userId1)")
    Optional<ChatEntity> findChatByUserIds(@Param("userId1") UUID userId1, @Param("userId2") UUID userId2);

    List<ChatEntity> findByUserId1OrUserId2(UUID userId1, UUID userId2);
}
