package com.example.swiftgathering_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingStrokeDTO {

    private Long sessionId;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private String color;
    private int thickness;
    private LocalDateTime createdAt;
}