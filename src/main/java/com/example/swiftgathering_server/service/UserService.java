package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.User;
import com.example.swiftgathering_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save() {
        User user = new User();
        userRepository.save(user);
    }
}
