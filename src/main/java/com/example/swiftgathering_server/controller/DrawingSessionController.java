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

    @PostMapping("/start")
    public ResponseEntity<DrawingSession> createSession(@RequestParam Long hostMemberId, @RequestParam Long guestMemberId) {
        try {
            DrawingSession newSession = drawingSessionService.createDrawingSession(hostMemberId, guestMemberId);
            return ResponseEntity.ok(newSession);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/end/{id}")
    public ResponseEntity<String> endSession(@PathVariable Long id) {
        try {
            drawingSessionService.findActiveSession(id);
            drawingSessionService.endDrawingSession(id);
            return ResponseEntity.ok().body("Session ended successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Session not found or already ended.");
        }
    }
}