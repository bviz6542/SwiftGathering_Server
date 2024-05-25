package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.FriendInfoDto;
import com.example.swiftgathering_server.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/{memberId}/friends")
    public ResponseEntity<Long> addFriendship(@PathVariable Long memberId, @RequestParam Long friendId) {
        friendshipService.saveFriendship(memberId, friendId);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/{memberId}/friends")
    public ResponseEntity<List<FriendInfoDto>> listAllFriends(@PathVariable Long memberId) {
        List<FriendInfoDto> friends = friendshipService.findAllFriendsOfUser(memberId)
                .stream()
                .map(member -> new FriendInfoDto(member.getId(), member.getName()))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(friends);
    }
}
