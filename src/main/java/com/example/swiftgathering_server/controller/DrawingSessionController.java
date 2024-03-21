package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.domain.DrawingSession;
import com.example.swiftgathering_server.service.DrawingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/drawing-sessions")
@RequiredArgsConstructor
public class DrawingSessionController {

    private final DrawingSessionService drawingSessionService;

    @PostMapping
    public ResponseEntity<DrawingSession> createSession(@RequestBody DrawingSession session) {
        DrawingSession savedSession = drawingSessionService.saveSession(session);
        return ResponseEntity.ok(savedSession);
    }
}
