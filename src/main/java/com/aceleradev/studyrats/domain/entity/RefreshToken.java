package com.aceleradev.studyrats.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    @Column(unique = true, nullable = false)
    private String token;

    private LocalDateTime expiresAt;

    private boolean revoked;

    private LocalDateTime createdAt;
}
