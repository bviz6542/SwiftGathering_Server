package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.FavoriteLocation;
import com.example.swiftgathering_server.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoriteLocationRepository {

    private final EntityManager em;

    public Long save(FavoriteLocation favoriteLocation) {
        em.persist(favoriteLocation);
        return favoriteLocation.getId();
    }

    public List<FavoriteLocation> findAllByMember(Member member) {
        return em.createQuery("select l from FavoriteLocation l where l.member = :member", FavoriteLocation.class)
                .setParameter("member", member)
                .getResultList();
    }
}