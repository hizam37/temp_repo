package com.social.Hizam.controller;

import com.social.Hizam.dto.RegistrationDto;
import com.social.Hizam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto register) {
       authenticationService.register(register);
       return ResponseEntity.ok().body("");
    }


}
