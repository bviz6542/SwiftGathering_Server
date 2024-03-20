package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Optional<Member> findByIdAndPassword(String id, String password) {
        return em.createQuery("select m from Member m where m.loginId = :id and m.loginPassword = :password", Member.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList()
                .stream()
                .findAny();
    }
}
