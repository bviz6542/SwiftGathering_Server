package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class LoginOutputDto {
    private final String token;
    private final Long memberId;

    public LoginOutputDto(String token, Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}