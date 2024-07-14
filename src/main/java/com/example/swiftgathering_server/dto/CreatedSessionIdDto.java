package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class CreatedSessionIdDto {
    private final String sessionId;

    public CreatedSessionIdDto(String sessionId) {
        this.sessionId = sessionId;
    }
}
