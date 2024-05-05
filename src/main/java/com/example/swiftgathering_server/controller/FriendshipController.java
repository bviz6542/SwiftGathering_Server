package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    public ResponseEntity<List<Member>> listAllFriends(@PathVariable Long memberId) {
        List<Member> friends = friendshipService.findAllFriendsOfUser(memberId);
        return ResponseEntity
                .ok(friends);
    }
}
