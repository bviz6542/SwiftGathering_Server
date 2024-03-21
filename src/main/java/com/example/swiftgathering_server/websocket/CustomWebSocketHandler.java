package com.example.swiftgathering_server.websocket;

import com.example.swiftgathering_server.service.DrawingStrokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import jakarta.annotation.PostConstruct;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ApplicationContext applicationContext;

    private DrawingStrokeService drawingStrokeService;

    @PostConstruct
    public void init() {
        drawingStrokeService = applicationContext.getBean(DrawingStrokeService.class);
    }
}
