package com.example.swiftgathering_server.dto.session;

import com.example.swiftgathering_server.domain.GatheringSessionMember;
import lombok.Getter;

import java.util.List;

@Getter
public class InviteToSessionRequestDto {
    private List<GatheringSessionMember> guests;
}