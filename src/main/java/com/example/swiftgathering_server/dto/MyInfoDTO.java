package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class MyInfoDTO {
    private Long id;

    public MyInfoDTO(Long id) {
        this.id = id;
    }
}