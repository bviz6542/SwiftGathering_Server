package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "draiwng_session")
public class DrawingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hostMemberId;
    private Long guestMemberId;

    private boolean isActive = true;

    @CreatedDate
    private LocalDateTime startTime;

    @LastModifiedDate
    private LocalDateTime endTime;

    @Builder
    DrawingSession(Long hostMemberId, Long guestMemberId) {
        this.hostMemberId = hostMemberId;
        this.guestMemberId = guestMemberId;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}