package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long earlierUserId;
    private Long laterUserId;

    @Builder
    Friendship(Long earlierUserId, Long laterUserId) {
        this.earlierUserId = earlierUserId;
        this.laterUserId = laterUserId;
    }
}