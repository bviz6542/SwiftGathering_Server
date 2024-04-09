package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final MemberRepository memberRepository;

    public void saveFriendship(Long memberId, Long friendId) {
        Member member = memberRepository.findOne(memberId);
        Member friend = memberRepository.findOne(friendId);

        Friendship.FriendshipBuilder builder = Friendship.builder();
        if (friendId > memberId) {
            builder
                    .youngerMember(friend)
                    .olderMember(member);
        } else {
            builder
                    .youngerMember(member)
                    .olderMember(friend);
        }
        Friendship friendship = builder.build();
        friendshipRepository.save(friendship);
    }

    @Transactional(readOnly = true)
    public List<Member> findAllFriendsOfUser(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        return friendshipRepository.findAllFriendsOfUser(member);
    }
}
