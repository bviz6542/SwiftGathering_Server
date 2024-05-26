package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class FavoriteLocationInputDto {
    private Long memberId;
    private Double latitude;
    private Double longtitude;
    private String name;
    private String description;
}