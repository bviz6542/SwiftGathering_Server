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

    public Optional<Member> findOne(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Optional<Member> findByLoginUsername(String loginUsername) {
        return em.createQuery("select m from Member m where m.loginUsername = :loginUsername", Member.class)
                .setParameter("loginUsername", loginUsername)
                .getResultList()
                .stream()
                .findAny();
    }

    public void remove(Member member) {
        em.remove(member);
    }
}
