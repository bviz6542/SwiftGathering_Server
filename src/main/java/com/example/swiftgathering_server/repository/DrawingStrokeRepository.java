package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.DrawingStroke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingStrokeRepository extends JpaRepository<DrawingStroke, Long> {
}