package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FriendshipRepository {

    private final EntityManager em;

    public Long save(Friendship friendship) {
        em.persist(friendship);
        return friendship.getId();
    }

    public List<Member> findAllFriendsOfUser(Member member) {
        List<Member> friends1 = em.createQuery("select f.senderMember from Friendship f where f.receiverMember = :member", Member.class)
                .setParameter("member", member)
                .getResultList();
        List<Member> friends2 = em.createQuery("select f.receiverMember from Friendship f where f.senderMember = :member", Member.class)
                .setParameter("member", member)
                .getResultList();
        Set<Member> allFriends = new HashSet<>(friends1);
        allFriends.addAll(friends2);
        allFriends.remove(member);
        return new ArrayList<>(allFriends)
                .stream()
                .sorted(Comparator.comparing(Member::getId))
                .toList();
    }
}
