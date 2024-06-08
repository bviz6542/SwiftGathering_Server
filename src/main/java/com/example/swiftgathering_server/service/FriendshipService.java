package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.FriendInfoDto;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<FriendInfoDto> findAllFriendsOfUser(Long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        List<Member> friends = friendshipRepository.findAllFriendsOfUser(member);
        return friends.stream()
                .map(friend -> new FriendInfoDto(friend.getId(), friend.getName()))
                .collect(Collectors.toList());
    }
}