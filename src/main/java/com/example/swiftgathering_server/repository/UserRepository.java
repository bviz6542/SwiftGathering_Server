package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByIdAndPassword(String id, String password) {
        return em.createQuery("select u from User u where u.loginId = :id and u.loginPassword = :password", User.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList()
                .stream()
                .findAny();
    }
}
