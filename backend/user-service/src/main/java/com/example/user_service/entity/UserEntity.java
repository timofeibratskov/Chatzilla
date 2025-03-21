package com.example.user_service.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.ToString;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
