package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class FriendRequestOutputDto {
    private Long id;
    private Long senderId;

    public FriendRequestOutputDto(Long id, Long senderId) {
        this.id = id;
        this.senderId = senderId;
    }
}
