package com.example.swiftgathering_server.dto;

import com.example.swiftgathering_server.domain.GatheringSessionMember;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateSessionRequestDto {
    private List<GatheringSessionMember> members;
}