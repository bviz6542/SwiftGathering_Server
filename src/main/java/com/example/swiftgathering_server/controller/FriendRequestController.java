package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.FriendRequestCreateDto;
import com.example.swiftgathering_server.dto.FriendRequestOutputDto;
import com.example.swiftgathering_server.dto.FriendRequestUpdateDto;
import com.example.swiftgathering_server.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members/{memberId}/friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping
    public ResponseEntity<Void> sendFriendRequest(@PathVariable Long memberId, @RequestBody FriendRequestCreateDto friendRequestCreateDto) {
        friendRequestService.sendFriendRequest(memberId, friendRequestCreateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FriendRequestOutputDto>> getAllPendingFriendRequests(@PathVariable Long memberId) {
         List<FriendRequestOutputDto> friendRequestOutputDtoList = friendRequestService.getAllPendingFriendRequests(memberId);
         return ResponseEntity.ok(friendRequestOutputDtoList);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateFriendRequest(@PathVariable Long memberId, @RequestBody FriendRequestUpdateDto friendRequestUpdateDto) {
        friendRequestService.updateFriendRequestStatus(memberId, friendRequestUpdateDto);
        return ResponseEntity.ok().build();
    }
}
