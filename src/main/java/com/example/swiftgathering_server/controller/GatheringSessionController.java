package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.ParticipateSessionRequestDto;
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

    @PatchMapping
    public ResponseEntity<Void> participateSession(@RequestBody ParticipateSessionRequestDto participateSessionRequestDto) {
        gatheringSessionService.participateSession(participateSessionRequestDto);
        return ResponseEntity.ok().build();
    }
}