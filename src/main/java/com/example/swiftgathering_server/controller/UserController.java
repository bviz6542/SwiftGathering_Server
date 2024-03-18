package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.WebSocketHandler;
import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping ("/register")
    public ResponseEntity<Void> register(@RequestBody LoginDto loginDto) {
        String id = loginDto.getId();
        String password = loginDto.getPassword();
        userService.register(id, password);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        String id = loginDto.getId();
        String password = loginDto.getPassword();
        try {
            userService.verify(id, password);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .build();
        }
    }
}
