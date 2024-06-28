package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FlagLocation;
import com.example.swiftgathering_server.domain.GatheringSession;
import com.example.swiftgathering_server.domain.GatheringSessionMember;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.session.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.session.EndSessionRequestDto;
import com.example.swiftgathering_server.dto.session.GatheringSessionNotificationDto;
import com.example.swiftgathering_server.dto.session.InviteToSessionRequestDto;
import com.example.swiftgathering_server.repository.FlagLocationRepository;
import com.example.swiftgathering_server.repository.GatheringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
    private final SimpMessageSendingOperations messagingTemplate;

    public void createSession(CreateSessionRequestDto requestDto) {
        List<GatheringSessionMember> members = requestDto.getParticipants();
        LocalDateTime createdTime = LocalDateTime.now();
        GatheringSession session = GatheringSession.builder()
                .gatheringSessionMembers(members)
                .createdTime(createdTime)
                .build();
        GatheringSession createdSession = gatheringSessionRepository.save(session);
        notifyClients(createdSession);
    }

    public void inviteToSession(Long sessionId, InviteToSessionRequestDto requestDto) {
        GatheringSession session = gatheringSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NoSuchElementException("Session not found"));

        List<GatheringSessionMember> members = requestDto.getGuests();
        notifyClients(members);
    }

    private void notifyClients(GatheringSession session) {
        List<Long> memberIds = session.getGatheringSessionMembers().stream()
                .map(GatheringSessionMember::getMember)
                .map(Member::getId)
                .collect(Collectors.toList());

        for (Long memberId: memberIds) {
            GatheringSessionNotificationDto notification = new GatheringSessionNotificationDto(session.getId(), memberIds);
            messagingTemplate.convertAndSend("/topic/private/" + memberId, notification);
        }
    }

    private void notifyClients(List<GatheringSessionMember> members) {
        GatheringSession gatheringSession = members.stream()
                .map(GatheringSessionMember::getGatheringSession).findAny()
                .orElseThrow(() -> new NoSuchElementException("Session not found"));
        List<Long> memberIds = members.stream().map(GatheringSessionMember::getMember).map(Member::getId).toList();

        for (Long memberId: memberIds) {
            GatheringSessionNotificationDto notification = new GatheringSessionNotificationDto(gatheringSession.getId(), memberIds);
            messagingTemplate.convertAndSend("/topic/private/" + memberId, notification);
        }
    }

    public void endSession(Long sessionId, EndSessionRequestDto requestDto) {
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

        gatheringSessionRepository.save(session);
        return;
    }
}