package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class FriendInfoDto {
    private Long id;
    private String name;

    public FriendInfoDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}