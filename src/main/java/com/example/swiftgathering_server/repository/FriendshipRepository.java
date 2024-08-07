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
        return em.createQuery("select m from Member m " +
                "where m in (select f.senderMember from Friendship f where f.receiverMember = :member) " +
                "or m in (select f.receiverMember from Friendship f where f.senderMember = :member)", Member.class)
                .setParameter("member", member)
                .getResultList();
    }
}

