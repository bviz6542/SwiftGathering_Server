package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.DrawingStroke;
import com.example.swiftgathering_server.repository.DrawingStrokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DrawingStrokeService {

    private final DrawingStrokeRepository drawingStrokeRepository;

    public DrawingStroke saveStroke(DrawingStroke stroke) {
        return drawingStrokeRepository.save(stroke);
    }
}