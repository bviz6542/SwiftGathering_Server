package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class DrawingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "host_member_id", nullable = false)
    private Member hostMember;

    @ManyToOne
    @JoinColumn(name = "guest_memeber_id", nullable = false)
    private Member guestMember;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}