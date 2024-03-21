package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.repository.DrawingSessionRepository;
import com.example.swiftgathering_server.domain.DrawingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DrawingSessionService {

    private final DrawingSessionRepository drawingSessionRepository;

    public DrawingSession saveSession(DrawingSession session) {
        return drawingSessionRepository.save(session);
    }
}