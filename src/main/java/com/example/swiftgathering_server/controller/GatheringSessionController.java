package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.EndSessionRequestDto;
import com.example.swiftgathering_server.service.GatheringSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gatherings")
@RequiredArgsConstructor
public class GatheringSessionController {

    private final GatheringSessionService gatheringSessionService;

    @PostMapping
    public ResponseEntity<Void> createSession(@RequestBody CreateSessionRequestDto requestDto) {
        gatheringSessionService.createSession(requestDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> endSession(@PathVariable Long id, @RequestBody EndSessionRequestDto requestDto) {
        gatheringSessionService.endSession(id, requestDto);
        return ResponseEntity.ok().build();
    }
}