package com.example.message_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "message_table")
public class MessageEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "chat_id")
    private UUID chatId;

    @Column(name = "text")
    private String text;

    @Column(name = "sender_id")
    private UUID senderId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = true)
    private LocalDateTime updatedAt;
}
