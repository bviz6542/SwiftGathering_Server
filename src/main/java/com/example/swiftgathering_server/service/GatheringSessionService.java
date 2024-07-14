package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.*;
import com.example.swiftgathering_server.dto.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.GatheringSessionNotificationDto;
import com.example.swiftgathering_server.dto.ParticipateSessionRequestDto;
import com.example.swiftgathering_server.exception.ResourceNotFoundException;
import com.example.swiftgathering_server.repository.GatheringSessionRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GatheringSessionService {

    private final MemberRepository memberRepository;
    private final GatheringSessionRepository gatheringSessionRepository;
//    private final FlagLocationRepository flagLocationRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    public void createSession(CreateSessionRequestDto requestDto) {
        List<GatheringSessionMember> sessionMembers = memberRepository
                .findAllByIds(requestDto.getGuestIds()).stream()
                .map(member -> GatheringSessionMember.builder()
                        .memberId(member.getId())
                        .status(GatheringSessionMemberStatus.INVITED)
                        .build())
                .collect(Collectors.toList());

        LocalDateTime createdTime = LocalDateTime.now();
        GatheringSession session = GatheringSession.builder()
                .gatheringSessionMembers(sessionMembers)
                .createdTime(createdTime)
                .build();

        GatheringSession savedSession = gatheringSessionRepository.save(session);
        notifyClients(savedSession);
    }

    public void participateSession(ParticipateSessionRequestDto requestDto) {
        GatheringSession session = gatheringSessionRepository.findById(requestDto.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        session.getGatheringSessionMembers().stream()
                .filter(member -> member.getMemberId().equals(requestDto.getMemberId()))
                .findFirst()
                .ifPresentOrElse(
                        member -> member.setStatus(GatheringSessionMemberStatus.PARTICIPATED),
                        () -> {
                            throw new IllegalStateException("Member not invited to the session");
                        });

        gatheringSessionRepository.update(session);
    }

    private void notifyClients(GatheringSession session) {
        List<Long> memberIds = session.getGatheringSessionMembers().stream()
                .map(GatheringSessionMember::getMemberId)
                .collect(Collectors.toList());

        for (Long memberId : memberIds) {
            GatheringSessionNotificationDto notification = new GatheringSessionNotificationDto(session.getId(), memberIds);
            messagingTemplate.convertAndSend("/topic/private." + memberId, notification);
        }
    }
}