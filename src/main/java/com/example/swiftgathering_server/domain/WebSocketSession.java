package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WebSocketSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String webSocketSessionId;

    private Long hostMemberId;
    private Long guestMemberId;

    @ManyToOne
    private DrawingSession drawingSession;
}