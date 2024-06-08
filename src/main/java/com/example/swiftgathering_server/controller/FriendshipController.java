package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.FriendInfoDto;
import com.example.swiftgathering_server.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members/{memberId}/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;
    
    @GetMapping
    public ResponseEntity<List<FriendInfoDto>> listAllFriends(@PathVariable Long memberId) {
        List<FriendInfoDto> friends = friendshipService.findAllFriendsOfUser(memberId);
        return ResponseEntity.ok(friends);
    }
}