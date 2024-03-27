package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping ("/members")
    public ResponseEntity<Void> register(@RequestBody LoginDto loginDto) {
        memberService.register(loginDto.getId(), loginDto.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        try {
            memberService.verify(loginDto.getId(), loginDto.getPassword());
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
}
