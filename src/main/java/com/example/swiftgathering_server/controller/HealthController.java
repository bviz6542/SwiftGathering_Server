package com.example.swiftgathering_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity
                .ok("v1.0.4");
    }
}
