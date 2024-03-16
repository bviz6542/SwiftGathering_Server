package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public Long save(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    public User findOne(Long id) {
        return entityManager.find(User.class, id);
    }
}
