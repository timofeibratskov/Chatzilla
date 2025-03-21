package com.example.user_service.entity;

import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.NonNull;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(
        name = "user_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "gmail"),
                @UniqueConstraint(columnNames = "tag")
        }
)
public class UserEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    @NonNull
    private String username;

    @Column(name = "gmail", nullable = false, unique = true)
    @NonNull
    private String gmail;

    @Column(name = "password", nullable = false,unique = false)
    @NonNull
    private String password;

    @Column(name = "tag", unique = true,nullable = true)
    private String tag;
}