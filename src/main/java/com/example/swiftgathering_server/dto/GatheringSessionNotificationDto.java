package com.example.swiftgathering_server.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GatheringSessionNotificationDto {
    final private String sessionId;
    final private List<Long> participantIds;

    public GatheringSessionNotificationDto(String sessionId, List<Long> participantIds) {
        this.sessionId = sessionId;
        this.participantIds = participantIds;
    }
}