package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.GatheringSession;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GatheringSessionRepository {

    private final EntityManager em;

    public GatheringSession save(GatheringSession gatheringSession) {
        em.persist(gatheringSession);
        return gatheringSession;
    }

    public Optional<GatheringSession> findById(Long id) {
        GatheringSession gatheringSession = em.find(GatheringSession.class, id);
        return Optional.of(gatheringSession);
    }
}