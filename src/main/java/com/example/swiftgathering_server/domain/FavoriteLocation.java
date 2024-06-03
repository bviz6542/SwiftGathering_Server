package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite_location")
public class FavoriteLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longtitude", nullable = false)
    private Double longtitude;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Builder
    FavoriteLocation(Member member, Double latitude, Double longtitude, String name, String description) {
        this.member = member;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.name = name;
        this.description = description;
    }
}