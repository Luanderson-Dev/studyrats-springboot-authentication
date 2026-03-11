package com.aceleradev.studyrats.controller;

import com.aceleradev.studyrats.dto.request.LoginRequest;
import com.aceleradev.studyrats.dto.request.RefreshRequest;
import com.aceleradev.studyrats.dto.request.RegisterRequest;
import com.aceleradev.studyrats.dto.response.AuthResponse;
import com.aceleradev.studyrats.service.AuthService;
import com.aceleradev.studyrats.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            AuthService authService,
            RefreshTokenService refreshTokenService
    ){
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Registrar novo usuário")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @Operation(summary = "Login do usuário")
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ){
        return authService.login(request);
    }

    @Operation(summary = "Refresh Token do usuário")
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request){
        return authService.refresh(request);
    }

    @Operation(summary = "Logout do usuário")
    @PostMapping("/logout")
    public void logout(@RequestBody RefreshRequest request){

        var refreshToken =
                refreshTokenService.validate(request.refreshToken());
        refreshTokenService.revokeAllUserTokens(
                refreshToken.getUserId()
        );
    }
}
