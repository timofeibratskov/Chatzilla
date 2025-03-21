package com.example.chat_service.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "chat_table")
@Entity
public class ChatEntity {

    @Id
    private UUID id;

    @Column(name = "user_id_1", nullable = false)  // Соответствует столбцу в БД
    private UUID userId1;  // camelCase вместо userId_1

    @Column(name = "user_id_2", nullable = false)  // Соответствует столбцу в БД
    private UUID userId2;  // camelCase вместо userId_2
}


