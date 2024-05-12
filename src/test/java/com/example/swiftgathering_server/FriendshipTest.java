package com.example.swiftgathering_server;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import jakarta.persistence.EntityManager;
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

    @Autowired
    private EntityManager em;

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
        em.flush();
 
        // when
        Friendship friendshipOf1And2 = Friendship.builder()
                .olderMember(member1)
                .youngerMember(member2)
                .build();
        Friendship friendshipOf2And3 = Friendship.builder()
                .olderMember(member2)
                .youngerMember(member3)
                .build();
        friendshipRepository.save(friendshipOf1And2);
        friendshipRepository.save(friendshipOf2And3);
        em.flush();

        // then
        List<Long> fetchedFriends = friendshipRepository.findAllFriendsOfUser(member2)
                .stream()
                .map(Member::getId)
                .toList();
        List<Long> expectedFriends = List.of(member1Id, member3Id);
        Assertions.assertArrayEquals(expectedFriends.toArray(), fetchedFriends.toArray());
    } 
}
