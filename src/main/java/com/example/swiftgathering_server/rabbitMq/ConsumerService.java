package com.example.swiftgathering_server.rabbitMq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    @RabbitListener(queues = "hello.queue")
    public void receiveMessage(String msg) {

    }
}