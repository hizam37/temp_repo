package com.social.Hizam.service;

import com.social.Hizam.dto.AuthenticateDto;
import com.social.Hizam.dto.RegistrationDto;
import com.social.Hizam.model.Role;
import com.social.Hizam.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public void register(RegistrationDto registrationDto)
    {

        if (!registrationDto.password().equals(registrationDto.password2())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .email(registrationDto.email())
                .firstName(registrationDto.firstName())
                .lastName(registrationDto.lastName())
                .password(passwordEncoder.encode(registrationDto.password()))
                .deleted(false)
                .captchaCode(registrationDto.captchaCode())
                .captchaSecret(registrationDto.captchaSecret())
                .roles(List.of(Role.ROLE_USER)).build();
        userService.create(user);

    }

    public void login(AuthenticateDto authenticateDto)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateDto.getEmail(),authenticateDto.getPassword()));
    }


}
