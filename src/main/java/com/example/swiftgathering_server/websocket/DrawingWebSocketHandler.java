package com.example.swiftgathering_server.websocket;

import com.example.swiftgathering_server.domain.DrawingSession;
import com.example.swiftgathering_server.dto.DrawingStrokeDto;
import com.example.swiftgathering_server.service.DrawingSessionService;
import com.example.swiftgathering_server.service.DrawingStrokeService;
import com.example.swiftgathering_server.domain.DrawingStroke;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DrawingWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Long, WebSocketSession[]> sessionParticipants = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long drawingSessionId = extractDrawingSessionId(session);
        sessionParticipants.compute(drawingSessionId, (key, value) -> {
            if (value == null) {
                return new WebSocketSession[]{session, null};
            } else {
                value[1] = session;
                return value;
            }
        });
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        Long drawingSessionId = extractDrawingSessionId(session);
        WebSocketSession[] participants = sessionParticipants.get(drawingSessionId);
        try {
            if (participants != null) {
                for (WebSocketSession participant : participants) {
                    if (participant != null && !participant.getId().equals(session.getId())) {
                        participant.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long drawingSessionId = extractDrawingSessionId(session);
        sessionParticipants.remove(drawingSessionId);
    }

    private Long extractDrawingSessionId(WebSocketSession session) {
        try {
            String path = session.getUri().getPath();
            String drawingSessionIdStr = path.substring(path.lastIndexOf('/') + 1);
            return Long.parseLong(drawingSessionIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}