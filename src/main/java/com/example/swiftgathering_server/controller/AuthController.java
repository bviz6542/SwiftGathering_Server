package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.LoginInputDto;
import com.example.swiftgathering_server.dto.LoginOutputDto;
import com.example.swiftgathering_server.service.AuthService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoginOutputDto> login(@RequestBody LoginInputDto loginInputDto) {
        LoginOutputDto loginOutputDto = authService.authenticateAndGenerateToken(loginInputDto);
        return ResponseEntity.ok(loginOutputDto);
    }
}
