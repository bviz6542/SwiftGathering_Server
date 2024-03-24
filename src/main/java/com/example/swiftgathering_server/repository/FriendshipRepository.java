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

    public List<Member> findAllFriendsOfUser(Long memberId) {
        List<Member> friendsPart1 = em.createQuery(
                        "SELECT m FROM Member m WHERE m.id IN (SELECT f.laterUserId FROM Friendship f WHERE f.earlierUserId = :memberId)", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();

        List<Member> friendsPart2 = em.createQuery(
                        "SELECT m FROM Member m WHERE m.id IN (SELECT f.earlierUserId FROM Friendship f WHERE f.laterUserId = :memberId)", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();

        Set<Member> allFriends = new HashSet<>(friendsPart1);
        allFriends.addAll(friendsPart2);

        return new ArrayList<>(allFriends)
                .stream()
                .sorted(Comparator.comparing(Member::getId))
                .toList();
    }
}
