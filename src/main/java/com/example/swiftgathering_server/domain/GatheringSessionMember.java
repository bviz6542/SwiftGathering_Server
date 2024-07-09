package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class GatheringSessionMember {
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private GatheringSessionMemberStatus status;

    @Builder
    public GatheringSessionMember(Long memberId, GatheringSessionMemberStatus status) {
        this.memberId = memberId;
        this.status = status;
    }

    public void setStatus(GatheringSessionMemberStatus status) {
        this.status = status;
    }
}
