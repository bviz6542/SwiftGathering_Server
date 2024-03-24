package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public void saveFriendship(Long userId, Long friendId) {
        Friendship.FriendshipBuilder builder = Friendship.builder();

        if (friendId > userId) {
            builder
                    .earlierUserId(userId)
                    .laterUserId(friendId);
        } else {
            builder
                    .earlierUserId(friendId)
                    .laterUserId(userId);
        }

        Friendship friendship = builder.build();
        friendshipRepository.save(friendship);
    }

    @Transactional(readOnly = true)
    public List<Member> findAllFriendsOfUser(Long memberId) {
        return friendshipRepository.findAllFriendsOfUser(memberId);
    }
}
