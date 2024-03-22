package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.DrawingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrawingSessionRepository extends JpaRepository<DrawingSession, Long> {
    Optional<DrawingSession> findBySessionId(Long sessionId);
}