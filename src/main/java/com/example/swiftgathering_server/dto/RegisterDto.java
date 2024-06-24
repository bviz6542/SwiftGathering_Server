package com.example.swiftgathering_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterDto {
    private String loginUsername;
    private String loginPassword;
    private String name;
}
