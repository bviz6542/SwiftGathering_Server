package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class DrawingStroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strokeId;

    private Long sessionId;
    private int startX, startY, endX, endY;
    private String color;
    private int thickness;
}