package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/location")
    public void location(LocationDto locationDto) {
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + locationDto.getChannelId(), locationDto);
    }
}