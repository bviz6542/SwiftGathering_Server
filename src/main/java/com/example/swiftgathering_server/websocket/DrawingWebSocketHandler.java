package com.example.swiftgathering_server.websocket;

import com.example.swiftgathering_server.dto.DrawingStrokeDTO;
import com.example.swiftgathering_server.service.DrawingSessionService;
import com.example.swiftgathering_server.service.DrawingStrokeService;
import com.example.swiftgathering_server.domain.DrawingStroke;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class DrawingWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(DrawingWebSocketHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private DrawingSessionService drawingSessionService;
    @Autowired
    private DrawingStrokeService drawingStrokeService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long drawingSessionId = extractDrawingSessionId(session);
        if (drawingSessionId != null) {
            drawingSessionService.registerSessionParticipant(drawingSessionId, session);
            logger.info("Registered session participant. Session ID: {}, Drawing Session ID: {}", session.getId(), drawingSessionId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long drawingSessionId = extractDrawingSessionId(session);
        if (drawingSessionId != null) {
            drawingSessionService.unregisterSessionParticipant(drawingSessionId, session);
            logger.info("Unregistered session participant. Session ID: {}, Drawing Session ID: {}", session.getId(), drawingSessionId);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            byte[] payload = message.getPayload().array();
            String jsonPayload = new String(payload, StandardCharsets.UTF_8);
            DrawingStrokeDTO strokeDTO = objectMapper.readValue(jsonPayload, DrawingStrokeDTO.class);
            DrawingStroke stroke = convertDTOToEntity(strokeDTO);
            drawingStrokeService.saveStroke(stroke);

            List<WebSocketSession> recipients = drawingSessionService.findActiveSessionsForBroadcast(stroke.getSessionId());
            for (WebSocketSession recipient : recipients) {
                if (!recipient.getId().equals(session.getId())) {
                    recipient.sendMessage(new TextMessage(objectMapper.writeValueAsString(strokeDTO)));
                }
            }
            logger.info("Drawing stroke saved and broadcasted. Session ID: {}", session.getId());
        } catch (IOException e) {
            logger.error("Error processing binary message. Session ID: {}", session.getId(), e);
        }
    }

    private DrawingStroke convertDTOToEntity(DrawingStrokeDTO strokeDTO) {
        DrawingStroke stroke = new DrawingStroke();
        stroke.setSessionId(strokeDTO.getSessionId());
        stroke.setStartX(strokeDTO.getStartX());
        stroke.setStartY(strokeDTO.getStartY());
        stroke.setEndX(strokeDTO.getEndX());
        stroke.setEndY(strokeDTO.getEndY());
        stroke.setColor(strokeDTO.getColor());
        stroke.setThickness(strokeDTO.getThickness());
        stroke.setCreatedAt(strokeDTO.getCreatedAt());
        return stroke;
    }

    private Long extractDrawingSessionId(WebSocketSession session) {
        try {
            return (Long) session.getAttributes().get("drawingSessionId");
        } catch (Exception e) {
            logger.error("Failed to extract drawing session ID from WebSocket session. Error: {}", e.getMessage());
            return null;
        }
    }

}