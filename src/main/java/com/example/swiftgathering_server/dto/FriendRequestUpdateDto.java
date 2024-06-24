package com.example.swiftgathering_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendRequestUpdateDto {
    private Long requestId;
    private Boolean isAccepted;
}
