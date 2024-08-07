package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GatheringSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID sessionId;

    private LocalDateTime createdTime;

    @ElementCollection
    private List<GatheringSessionMember> gatheringSessionMembers = new ArrayList<>();

    @OneToMany(mappedBy = "gatheringSession", cascade = CascadeType.ALL)
    private List<FlagLocation> locations;

    @Builder
    public GatheringSession(List<GatheringSessionMember> gatheringSessionMembers, LocalDateTime createdTime) {
        this.sessionId = UUID.randomUUID();
        this.gatheringSessionMembers = gatheringSessionMembers;
        this.createdTime = createdTime;
    }
}
