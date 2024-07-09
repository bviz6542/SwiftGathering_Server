package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class ParticipateSessionRequestDto {
    private Long sessionId;
    private Long memberId;
}
