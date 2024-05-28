package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FlagLocation;
import com.example.swiftgathering_server.domain.GatheringSession;
import com.example.swiftgathering_server.domain.GatheringSessionMember;
import com.example.swiftgathering_server.dto.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.EndSessionRequestDto;
import com.example.swiftgathering_server.repository.FlagLocationRepository;
import com.example.swiftgathering_server.repository.GatheringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GatheringSessionService {

    private final GatheringSessionRepository gatheringSessionRepository;
    private final FlagLocationRepository flagLocationRepository;

    public Long createSession(CreateSessionRequestDto requestDto) {
        List<GatheringSessionMember> members = requestDto.getMembers();
        LocalDateTime createdTime = LocalDateTime.now();
        GatheringSession session = GatheringSession.builder()
                .gatheringSessionMembers(members)
                .createdTime(createdTime)
                .build();
        return gatheringSessionRepository.save(session);
    }

    public Long endSession(Long sessionId, EndSessionRequestDto requestDto) {
        GatheringSession session = gatheringSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NoSuchElementException("Session not found"));

        LocalDateTime endedTime = LocalDateTime.now();

        List<FlagLocation> flagLocations = requestDto.getFlagLocations().stream()
                .map(dto -> FlagLocation.builder()
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .name(dto.getName())
                        .createdTime(dto.getCreatedTime() != null ? dto.getCreatedTime() : LocalDateTime.now())
                        .gatheringSession(session)
                        .build())
                .collect(Collectors.toList());

        session.endSession(endedTime, flagLocations);

        flagLocationRepository.saveAll(flagLocations);

        return gatheringSessionRepository.save(session);
    }
}