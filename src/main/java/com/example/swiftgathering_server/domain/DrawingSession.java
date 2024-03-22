package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class DrawingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    private Long hostMemberId;
    private Long guestMemberId;

    private boolean isActive = true;

//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
}