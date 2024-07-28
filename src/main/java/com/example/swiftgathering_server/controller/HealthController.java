package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.config.ApplicationStartup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<String> checkHealth() {
        Instant bootTime = ApplicationStartup.getStartTime();
        ZonedDateTime bootTimeKST = bootTime.atZone(ZoneId.of("Asia/Seoul"));
        String bootTimeFormatted = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").format(bootTimeKST);
        String response = String.format("boot time: %s", bootTimeFormatted);
        return ResponseEntity.ok(response);
    }
}