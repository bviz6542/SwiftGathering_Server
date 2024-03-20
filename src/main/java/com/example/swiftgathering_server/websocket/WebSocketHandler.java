package com.example.swiftgathering_server.websocket;

import com.example.swiftgathering_server.dto.DrawingInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.socket.BinaryMessage;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session);
        logger.info("New connection established. Session ID: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            CLIENTS.remove(session.getId());
            logger.info("Connection closed. Session ID: {}, Close Status: {}", session.getId(), status);
        } catch (NullPointerException e) {

        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Optionally parse the JSON message if you need to read or modify it
        // ObjectNode jsonNode = objectMapper.readValue(message.getPayload(), ObjectNode.class);

        // For broadcasting without modification, you can simply forward the message
        logger.info("Received message from Session ID: {}. Message: {}", session.getId(), message.getPayload());

        broadcastMessage(session, message);
    }

    private void broadcastMessage(WebSocketSession senderSession, TextMessage message) {
        CLIENTS.forEach((id, session) -> {
//            if (!session.getId().equals(senderSession.getId())) { // Avoid sending the message back to its sender
                try {
                    logger.info("Broadcasting message to Session ID: {}", session.getId());

                    session.sendMessage(message);
                } catch (IOException e) {
                    logger.error("Failed to send message to Session ID: {}. Error: {}", session.getId(), e.getMessage(), e);

                }
//            }
        });
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            byte[] payload = message.getPayload().array(); // Get the binary data as byte array
            String jsonPayload = new String(payload, StandardCharsets.UTF_8); // Convert byte array to String
            DrawingInfoDTO drawingInfo = objectMapper.readValue(jsonPayload, DrawingInfoDTO.class); // Deserialize JSON to object

            logger.info("Received drawing command from Session ID: {}. Coordinates: ({}, {}), Event: {}", session.getId(), drawingInfo.getX(), drawingInfo.getY(), drawingInfo.getEvent());
            broadcastDrawingInfo(drawingInfo, session.getId());

            // Handle the drawing info as needed (e.g., store, broadcast, etc.)
        } catch (IOException e) {
            logger.error("Failed to parse binary message from Session ID: {}. Error: {}", session.getId(), e.getMessage(), e);
        }
    }

    private void broadcastDrawingInfo(DrawingInfoDTO drawingInfo, String senderSessionId) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(drawingInfo);
            byte[] bytes = jsonPayload.getBytes(StandardCharsets.UTF_8);
            BinaryMessage message = new BinaryMessage(bytes);

            CLIENTS.forEach((sessionId, session) -> {
                if (!sessionId.equals(senderSessionId)) {
                    try {
                        session.sendMessage(message);
                        logger.info("Broadcasting drawing info as binary to Session ID: {}", sessionId);
                    } catch (IOException e) {
                        logger.error("Failed to broadcast drawing info as binary to Session ID: {}. Error: {}", sessionId, e.getMessage(), e);
                    }
                }
            });
        } catch (JsonProcessingException e) {
            logger.error("Error serializing drawing info to JSON. Error: {}", e.getMessage(), e);
        }
    }


}
