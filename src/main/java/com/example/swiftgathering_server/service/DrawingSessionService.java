package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.DrawingSession;
import com.example.swiftgathering_server.repository.DrawingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class DrawingSessionService {

    private final DrawingSessionRepository drawingSessionRepository;

    public DrawingSession createDrawingSession(Long hostMemberId, Long guestMemberId) {
        DrawingSession session = DrawingSession.builder()
                .hostMemberId(hostMemberId)
                .guestMemberId(guestMemberId)
                .build();
        return drawingSessionRepository.save(session);
    }

    public void endDrawingSession(Long id) {
        drawingSessionRepository.findById(id).ifPresent(session -> {
            session.setIsActive(false);
            drawingSessionRepository.delete(session);
        });
    }

    public void findActiveSession(Long id) {
        drawingSessionRepository.findById(id)
                .filter(DrawingSession::isActive)
                .orElseThrow(() -> new NoSuchElementException("Active drawing session not found for ID: " + id));
    }
}