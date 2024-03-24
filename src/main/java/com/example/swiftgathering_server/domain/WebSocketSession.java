package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "web_socket_session")
public class WebSocketSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String webSocketSessionId;

    private Long hostMemberId;
    private Long guestMemberId;

    @ManyToOne
    private DrawingSession drawingSession;

    @Builder
    WebSocketSession(String webSocketSessionId, Long hostMemberId, Long guestMemberId) {
        this.webSocketSessionId = webSocketSessionId;
        this.hostMemberId = hostMemberId;
        this.guestMemberId = guestMemberId;
    }
}