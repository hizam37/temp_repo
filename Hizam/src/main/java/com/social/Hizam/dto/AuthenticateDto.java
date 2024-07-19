package com.social.Hizam.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO аутентификации")
public record AuthenticateDto (
    String email,
    String password

){}

