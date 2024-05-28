package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatheringSessionMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gathering_session_id")
    private GatheringSession gatheringSession;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public GatheringSessionMember(GatheringSession gatheringSession, Member member) {
        this.gatheringSession = gatheringSession;
        this.member = member;
    }
}
