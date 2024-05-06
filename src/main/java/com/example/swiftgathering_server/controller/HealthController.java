package com.example.swiftgathering_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity
                .ok()
                .build();
    }
}
