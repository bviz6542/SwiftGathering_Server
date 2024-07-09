package com.example.swiftgathering_server.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateSessionRequestDto {
    private Long senderId;
    private List<Long> guestIds;
}