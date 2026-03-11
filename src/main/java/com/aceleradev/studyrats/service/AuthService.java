package com.aceleradev.studyrats.service;

import com.aceleradev.studyrats.config.JwtService;
import com.aceleradev.studyrats.domain.entity.RefreshToken;
import com.aceleradev.studyrats.domain.entity.User;
import com.aceleradev.studyrats.dto.request.LoginRequest;
import com.aceleradev.studyrats.dto.request.RefreshRequest;
import com.aceleradev.studyrats.dto.request.RegisterRequest;
import com.aceleradev.studyrats.dto.response.AuthResponse;
import com.aceleradev.studyrats.repository.UserRepository;
import com.aceleradev.studyrats.security.PasswordService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordService passwordService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ){
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new RuntimeException("email already registered");
        }

        String passwordHash =
                passwordService.hashPassword(request.password());

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(passwordHash)
                .build();

        userRepository.save(user);

        String accessToken =
                jwtService.generateToken(user.getId());

        RefreshToken refreshToken =
                refreshTokenService.create(user.getId());

        return new AuthResponse(accessToken, refreshToken.getToken(), "Bearer");
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow();

        if(!passwordService.verify(
                request.password(),
                user.getPasswordHash()
        )){
            throw new RuntimeException("invalid credentials");
        }

        String accessToken =
                jwtService.generateToken(user.getId());

        RefreshToken refreshToken =
                refreshTokenService.create(user.getId());

        return new AuthResponse(accessToken, refreshToken.getToken(), "Bearer");
    }

    public AuthResponse refresh(RefreshRequest request){
        RefreshToken refreshToken =
                refreshTokenService.validate(request.refreshToken());

        String newAccessToken =
                jwtService.generateToken(refreshToken.getUserId());

        RefreshToken newRefreshToken =
                refreshTokenService.rotate(refreshToken);

        return new AuthResponse(newAccessToken, refreshToken.getToken(), "Bearer");
    }
}
