package com.example.swiftgathering_server.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class RabbitMqConfig {
//
//    @Value("${spring.rabbitmq.host:localhost}")
//    private String host;
//
//    @Value("${spring.rabbitmq.username:guest}")
//    private String username;
//
//    @Value("${spring.rabbitmq.password}:guest")
//    private String password;
//
//    @Value("${spring.rabbitmq.port}61613")
//    private int port;
//
//    @Bean
//    public TopicExchange sessionExchange() {
//        return new TopicExchange("swift-gathering.exchange");
//    }
//
//    @Bean
//    public Queue sessionQueue() {
//        return new Queue("swift-gathering.queue");
//    }
//
//    @Bean
//    public Binding sessionBinding(Queue sessionQueue, TopicExchange sessionExchange) {
//        return BindingBuilder.bind(sessionQueue).to(sessionExchange).with("session.#");
//    }
//
//    @Bean
//    ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setPort(port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }
//
//    @Bean
//    MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//}