package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class LocationDto {
    private Long senderId;
    private Long channelId;
    private Double latitude;
    private Double longitude;
}
