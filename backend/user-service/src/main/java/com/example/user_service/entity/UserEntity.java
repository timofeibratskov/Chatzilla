package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private UUID id;
    @Column(name = "user_name")
    @NonNull
    private String username;
    @Column(name = "gmail")
    @NonNull
    private String gmail;
    @Column(name = "password")
    @NonNull
    private String password;
    @Column(name = "tag")
    @NonNull
    private String tag; // уникальное имя
}
