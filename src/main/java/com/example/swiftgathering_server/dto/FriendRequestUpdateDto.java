package com.example.swiftgathering_server.dto;

import lombok.Getter;

@Getter
public class FriendRequestUpdateDto {
    private Long requestId;
    private Boolean isAccepted;
}
