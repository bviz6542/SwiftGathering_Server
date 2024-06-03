package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FlagLocation;
import com.example.swiftgathering_server.domain.GatheringSession;
import com.example.swiftgathering_server.domain.GatheringSessionMember;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.CreateSessionRequestDto;
import com.example.swiftgathering_server.dto.EndSessionRequestDto;
import com.example.swiftgathering_server.dto.GatheringSessionNotificationDto;
import com.example.swiftgathering_server.repository.FlagLocationRepository;
import com.example.swiftgathering_server.repository.GatheringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.AmqpTemplate;
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
    private final RabbitAdmin rabbitAdmin;
    private final AmqpTemplate amqpTemplate;

    public void createSession(CreateSessionRequestDto requestDto) {
        List<GatheringSessionMember> members = requestDto.getMembers();
        LocalDateTime createdTime = LocalDateTime.now();
        GatheringSession session = GatheringSession.builder()
                .gatheringSessionMembers(members)
                .createdTime(createdTime)
                .build();
        GatheringSession sessionId = gatheringSessionRepository.save(session);
        notifyClients(sessionId);
        return;
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

    private void notifyClients(GatheringSession session) {
        List<Long> memberIds = session.getGatheringSessionMembers().stream()
                .map(GatheringSessionMember::getMember)
                .map(Member::getId)
                .collect(Collectors.toList());

        for (Long memberId : memberIds) {
            Queue queue = new Queue("swift-gathering.queue." + memberId, false);
            DirectExchange exchange = new DirectExchange("swift-gathering.exchange." + memberId);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with("swift-gathering.routing." + memberId);

            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareBinding(binding);

            GatheringSessionNotificationDto notification = new GatheringSessionNotificationDto(session.getId(), memberIds);

            amqpTemplate.convertAndSend(exchange.getName(), binding.getRoutingKey(), notification);
        }
    }
}