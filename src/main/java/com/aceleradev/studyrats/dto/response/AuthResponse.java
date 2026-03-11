package com.aceleradev.studyrats.dto.response;

public record AuthResponse(String accessToken, String refreshToken, String tokenType) {}
