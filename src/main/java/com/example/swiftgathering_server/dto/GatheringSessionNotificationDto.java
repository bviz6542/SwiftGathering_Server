package com.example.swiftgathering_server.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GatheringSessionNotificationDto {
    private Long sessionId;
    private List<Long> memberIds;

    public GatheringSessionNotificationDto(Long sessionId, List<Long> memberIds) {
        this.sessionId = sessionId;
        this.memberIds = memberIds;
    }
}