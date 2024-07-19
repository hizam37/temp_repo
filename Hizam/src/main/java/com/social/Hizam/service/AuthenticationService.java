package com.social.Hizam.service;

import com.social.Hizam.dto.AuthenticateDto;
import com.social.Hizam.dto.AuthenticateResponseDto;
import com.social.Hizam.dto.RegistrationDto;
import com.social.Hizam.model.Role;
import com.social.Hizam.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public void register(RegistrationDto registrationDto)
    {
        var user = User.builder()
                .userId(registrationDto.id())
                .firstName(registrationDto.firstName())
                .lastName(registrationDto.lastName())
                .password1(passwordEncoder.encode(registrationDto.password1()))
                .password2(passwordEncoder.encode(registrationDto.password2()))
                .deleted(false)
                .captchaCode(registrationDto.captchaCode())
                .captchaSecret(registrationDto.captchaSecret())
                .role(Role.ROLE_USER).build();
        userService.create(user);
    }

}
