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

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private DrawingSession session;

    @Column(columnDefinition = "JSON")
    private String strokeData;

    private LocalDateTime createdAt;
}