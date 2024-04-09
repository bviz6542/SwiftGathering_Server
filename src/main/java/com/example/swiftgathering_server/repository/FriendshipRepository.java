package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.FriendshipId;
import com.example.swiftgathering_server.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FriendshipRepository {

    private final EntityManager em;

    public FriendshipId save(Friendship friendship) {
        em.persist(friendship);
        return friendship.getId();
    }

    public List<Member> findAllFriendsOfUser(Member member) {
        List<Member> olderFriends = em.createQuery("select m from Member m join m.friendshipsWithOlderMember f", Member.class).getResultList();
        List<Member> youngerFriends = em.createQuery("select m from Member m join m.friendshipsWithYoungerMember f", Member.class).getResultList();

        Set<Member> allFriends = new HashSet<>(olderFriends);
        allFriends.addAll(youngerFriends);
        allFriends.remove(member);
        return new ArrayList<>(allFriends)
                .stream()
                .sorted(Comparator.comparing(Member::getId))
                .toList();
    }
}
