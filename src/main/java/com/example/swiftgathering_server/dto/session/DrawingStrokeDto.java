package com.example.swiftgathering_server.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingStrokeDto {

    private Long sessionId;
    private int startX, startY, endX, endY;
    private String color;
    private int thickness;
}