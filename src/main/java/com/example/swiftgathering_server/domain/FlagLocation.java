package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlagLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private String name;
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_session_id", nullable = false)
    private GatheringSession gatheringSession;

    @Builder
    public FlagLocation(double latitude, double longitude, String name, LocalDateTime createdTime, GatheringSession gatheringSession) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.createdTime = createdTime;
        this.gatheringSession = gatheringSession;
    }
}
