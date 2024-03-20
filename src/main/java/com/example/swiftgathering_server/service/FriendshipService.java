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
        if (friendId > userId) {
            Friendship friendship = new Friendship();
            friendship.setEarlierUserId(userId);
            friendship.setLaterUserId(friendId);
            friendshipRepository.save(friendship);

        } else {
            Friendship friendship = new Friendship();
            friendship.setEarlierUserId(friendId);
            friendship.setLaterUserId(userId);
            friendshipRepository.save(friendship);
        }
    }

    @Transactional(readOnly = true)
    public List<Member> findAllFriendsOfUser(Long memberId) {
        return friendshipRepository.findAllFriendsOfUser(memberId);
    }
}
