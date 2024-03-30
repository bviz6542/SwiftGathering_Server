package com.example.swiftgathering_server.rabbitMq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/drawing-strokes")
public class ProducerController {

    final private ProducerService producerService;

    @PostMapping("/publish")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageDto messageDto) {
        producerService.sendMessage(messageDto);
        return ResponseEntity
                .ok()
                .build();
    }
}