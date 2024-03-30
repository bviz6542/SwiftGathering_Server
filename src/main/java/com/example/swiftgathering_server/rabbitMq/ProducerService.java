package com.example.swiftgathering_server.rabbitMq;

public interface ProducerService {

    void sendMessage(MessageDto messageDto);
}