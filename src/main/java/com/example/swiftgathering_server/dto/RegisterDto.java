package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class RegisterDto {
    private String loginId;
    private String loginPassword;
    private String name;
}
