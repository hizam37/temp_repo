package com.social.Hizam.controller;

import com.social.Hizam.dto.*;
//import com.social.Hizam.service.KafkaProducer;
import jakarta.validation.Valid;
import com.social.Hizam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthenticationService authenticationService;

//    private final KafkaProducer kafkaProducer;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationDto register) {
        if(!Objects.isNull(register)) {
            authenticationService.register(register);
//            kafkaProducer.sendMessage(register.toString());
            return ResponseEntity.ok().body("Successful operation");
        }else {
            return ResponseEntity.ok().body(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthenticateResponseDto refresh) {
        return null;
    }


    @PostMapping("/password/recovery")
    public ResponseEntity<?> passwordRecovery(@RequestBody PasswordRecoveryDto passwordRecovery) {
        return null;
    }


    @PostMapping("/password/recovery/{linkId}")
    public ResponseEntity<RegistrationDto> passwordRecoveryById(@RequestBody NewPasswordDto newPasswordDto,@PathVariable  String linkid) {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticateDto authenticateDto)
    {
        authenticationService.login(authenticateDto);
        return ResponseEntity.ok("Successful operation");
    }


    @GetMapping("/captcha")
    public String getCaptcha()
    {
        return null;
    }


}
