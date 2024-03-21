package com.example.swiftgathering_server.websocket;

import com.example.swiftgathering_server.dto.DrawingStrokeDTO;
import com.example.swiftgathering_server.service.DrawingStrokeService;
import com.example.swiftgathering_server.domain.DrawingStroke;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private ApplicationContext applicationContext;
    private DrawingStrokeService drawingStrokeService;

    @PostConstruct
    public void init() {
        drawingStrokeService = applicationContext.getBean(DrawingStrokeService.class);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        CLIENTS.put(session.getId(), session);
        logger.info("New connection established. Session ID: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        CLIENTS.remove(session.getId());
        logger.info("Connection closed. Session ID: {}, Close Status: {}", session.getId(), status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message from Session ID: {}. Message: {}", session.getId(), message.getPayload());
        broadcastMessage(session, message);
    }

    private void broadcastMessage(WebSocketSession senderSession, TextMessage message) {
        CLIENTS.forEach((id, session) -> {
            if (!session.getId().equals(senderSession.getId())) {
                try {
                    logger.info("Broadcasting message to Session ID: {}", session.getId());
                    session.sendMessage(message);
                } catch (IOException e) {
                    logger.error("Failed to send message to Session ID: {}. Error: {}", session.getId(), e.getMessage());
                }
            }
        });
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            byte[] payload = message.getPayload().array();
            String jsonPayload = new String(payload, StandardCharsets.UTF_8);
            DrawingStrokeDTO strokeDTO = objectMapper.readValue(jsonPayload, DrawingStrokeDTO.class);
            DrawingStroke stroke = convertDTOToEntity(strokeDTO);
            drawingStrokeService.saveStroke(stroke);
        } catch (IOException e) {
            logger.error("Failed to parse binary message. Error: ", e);
        }
    }

    private DrawingStroke convertDTOToEntity(DrawingStrokeDTO strokeDTO) {
        return new DrawingStroke();
    }
}
