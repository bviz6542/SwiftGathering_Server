package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class MyInfoDto {
    private Long id;

    public MyInfoDto(Long id) {
        this.id = id;
    }
}