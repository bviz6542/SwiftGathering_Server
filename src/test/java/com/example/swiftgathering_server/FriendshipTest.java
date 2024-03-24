package com.example.swiftgathering_server;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class FriendshipTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 친구목록() {
        // given
        Member member1 = Member.builder()
                .loginId("111")
                .loginPassword("11111")
                .build();
        Member member2 = Member.builder()
                .loginId("222")
                .loginPassword("22222")
                .build();
        Member member3 = Member.builder()
                .loginId("333")
                .loginPassword("33333")
                .build();
        Long member1Id = memberRepository.save(member1);
        Long member2Id = memberRepository.save(member2);
        Long member3Id = memberRepository.save(member3);

        // when
        Friendship friendship1 = Friendship.builder()
                .earlierUserId(member1Id)
                .laterUserId(member2Id)
                .build();
        Friendship friendship2 = Friendship.builder()
                .earlierUserId(member2Id)
                .laterUserId(member3Id)
                .build();
        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);

        // then
        List<Long> fetchedFriends = friendshipRepository.findAllFriendsOfUser(member2Id)
                .stream()
                .map(Member::getId)
                .toList();
        List<Long> expectedFriends = List.of(member1Id, member3Id);
        Assertions.assertArrayEquals(fetchedFriends.toArray(), expectedFriends.toArray());
    }
}
