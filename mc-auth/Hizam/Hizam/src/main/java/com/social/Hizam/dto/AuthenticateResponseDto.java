package com.social.Hizam.dto;

public record AuthenticateResponseDto (
    String accessToken,
    String refreshToken){}

