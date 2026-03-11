package com.aceleradev.studyrats.service;

import com.aceleradev.studyrats.domain.entity.RefreshToken;
import com.aceleradev.studyrats.repository.RefreshTokenRepository;
import com.aceleradev.studyrats.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            UserRepository userRepository
    ){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public RefreshToken create(String userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        RefreshToken token = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        return repository.save(token);
    }

    public RefreshToken validate(String token){
        RefreshToken refreshToken = repository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("token not found"));

        if(refreshToken.isRevoked()){
            throw new RuntimeException("token revoked");
        }

        if(refreshToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token expired");
        }

        return refreshToken;
    }

    public RefreshToken rotate(RefreshToken oldToken){
        oldToken.setRevoked(true);
        repository.save(oldToken);

        return create(oldToken.getUserId());
    }

    public void revokeAllUserTokens(String userId){
        List<RefreshToken> tokens =
                repository.findAllByUserIdAndRevokedFalse(userId);

        tokens.forEach(token -> token.setRevoked(true));

        repository.saveAll(tokens);
    }
}