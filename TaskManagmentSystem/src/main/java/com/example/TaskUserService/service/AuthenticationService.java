package com.example.TaskUserService.service;


import com.example.TaskUserService.dto.JwtAuthenticationResponse;
import com.example.TaskUserService.dto.SignInRequest;
import com.example.TaskUserService.dto.SignUpRequest;
import com.example.TaskUserService.model.Role;
import com.example.TaskUserService.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import com.example.TaskUserService.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        var user = User.builder().email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(List.of(Role.ROLE_USER)).build();
        userService.createUser(user);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));
        var user = userService.userDetailsService().loadUserByUsername(signInRequest.getUsername());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
