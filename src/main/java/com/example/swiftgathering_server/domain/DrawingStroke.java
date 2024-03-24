package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "drawing_stroke")
public class DrawingStroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long strokeId;

    private Long sessionId;
    private int startX, startY, endX, endY;
    private String color;
    private int thickness;

    @Builder
    DrawingStroke(Long sessionId, int startX, int startY, int endX, int endY, String color, int thickness) {
        this.sessionId = sessionId;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.thickness = thickness;
    }
}