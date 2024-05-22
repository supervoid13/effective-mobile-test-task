package com.uneev.testtaskem.controller;

import com.uneev.testtaskem.dto.JwtResponseDto;
import com.uneev.testtaskem.dto.UserLoginDto;
import com.uneev.testtaskem.dto.UserRegistrationDto;
import com.uneev.testtaskem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        String token = authService.createToken(userLoginDto.getUsername(), userLoginDto.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        authService.createUser(userRegistrationDto);

        String token = authService.createToken(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getPassword()
        );

        return new ResponseEntity<>(
                new JwtResponseDto(token),
                HttpStatus.CREATED
        );
    }
}
