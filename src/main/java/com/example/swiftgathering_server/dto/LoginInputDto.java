package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class LoginInputDto {
    private String loginUsername;
    private String loginPassword;

    public LoginInputDto(String loginUsername, String loginPassword) {
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }
}
