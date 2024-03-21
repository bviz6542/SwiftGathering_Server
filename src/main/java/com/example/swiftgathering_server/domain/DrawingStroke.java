package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class DrawingStroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strokeId;

    private Long sessionId; // Reference to the drawing session
    private int startX, startY, endX, endY; // Coordinates
    private String color; // Stroke color
    private int thickness; // Stroke thickness

    private LocalDateTime createdAt;
}