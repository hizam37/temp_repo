package com.social.Hizam.dto;


import lombok.Data;


import java.util.UUID;

public record RegistrationDto (
    UUID uuid,
    boolean deleted,
    String email,
    String password,
    String password2,
    String firstName,
    String lastName,
    String captchaCode,
    String captchaSecret
){};