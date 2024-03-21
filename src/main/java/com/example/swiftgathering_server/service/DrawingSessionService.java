package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.repository.DrawingSessionRepository;
import com.example.swiftgathering_server.domain.DrawingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class DrawingSessionService {

    private final ConcurrentHashMap<Long, List<WebSocketSession>> sessionParticipants = new ConcurrentHashMap<>();
    private final DrawingSessionRepository drawingSessionRepository;

    public DrawingSession createDrawingSession(DrawingSession session) {
        return drawingSessionRepository.save(session);
    }

    public void registerSessionParticipant(Long drawingSessionId, WebSocketSession webSocketSession) {
        sessionParticipants.computeIfAbsent(drawingSessionId, k -> new CopyOnWriteArrayList<>()).add(webSocketSession);
    }

    public void unregisterSessionParticipant(Long drawingSessionId, WebSocketSession webSocketSession) {
        sessionParticipants.computeIfPresent(drawingSessionId, (k, v) -> {
            v.remove(webSocketSession);
            return v.isEmpty() ? null : v;
        });
    }

    public List<WebSocketSession> findActiveSessionsForBroadcast(Long drawingSessionId) {
        return sessionParticipants.getOrDefault(drawingSessionId, new CopyOnWriteArrayList<>());
    }
}