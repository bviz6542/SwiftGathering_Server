package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.DrawingSession;
import com.example.swiftgathering_server.repository.DrawingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class DrawingSessionService {

    private final DrawingSessionRepository drawingSessionRepository;

    public DrawingSession createDrawingSession(Long hostMemberId, Long guestMemberId) {
        DrawingSession session = new DrawingSession();
        session.setHostMemberId(hostMemberId);
        session.setGuestMemberId(guestMemberId);
        session.setActive(true);
        return drawingSessionRepository.save(session);
    }

    public void endDrawingSession(Long sessionId) {
        drawingSessionRepository.findBySessionId(sessionId).ifPresent(session -> {
            session.setActive(false);
            drawingSessionRepository.delete(session);
        });
    }

    public void findActiveSession(Long sessionId) {
        drawingSessionRepository.findBySessionId(sessionId)
                .filter(DrawingSession::isActive)
                .orElseThrow(() -> new NoSuchElementException("Active drawing session not found for ID: " + sessionId));
    }
}