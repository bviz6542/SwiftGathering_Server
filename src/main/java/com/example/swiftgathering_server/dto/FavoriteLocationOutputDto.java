package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class FavoriteLocationOutputDto {
    private Long id;
    private Double latitude;
    private Double longtitude;
    private String name;
    private String description;

    public FavoriteLocationOutputDto(Long id, Double latitude, Double longtitude, String name, String description) {
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.name = name;
        this.description = description;
    }
}