package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.WebSocketHandler;
import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @PostMapping ("/register")
    public ResponseEntity<Void> register(@RequestBody LoginDto loginDto) {
        String id = loginDto.getId();
        String password = loginDto.getPassword();

        logger.info("id: {}, password: {}", id, password);

        userService.register(id, password);

        return ResponseEntity
                .ok()
                .build();
    }
}
