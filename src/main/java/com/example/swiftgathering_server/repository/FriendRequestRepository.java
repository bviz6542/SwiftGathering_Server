package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.FriendRequest;
import com.example.swiftgathering_server.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendRequestRepository {

    private final EntityManager em;

    public Long save(FriendRequest friendRequest) {
        em.persist(friendRequest);
        return friendRequest.getId();
    }

    public Optional<FriendRequest> findById(Long requestId) {
        FriendRequest friendRequest = em.find(FriendRequest.class, requestId);
        return Optional.of(friendRequest);
    }

    public List<FriendRequest> getAllPendingFriendRequests(Member member) {
        return em.createQuery("select f from FriendRequest f where f.receiverMember = :receiver and f.requestStatus = FriendRequest.RequestStatus.ACCEPTED", FriendRequest.class)
                .setParameter("receiver", member)
                .getResultList();
    }
}
