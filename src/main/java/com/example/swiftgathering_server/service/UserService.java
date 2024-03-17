package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.WebSocketHandler;
import com.example.swiftgathering_server.domain.User;
import com.example.swiftgathering_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Transactional
    public void register(String loginId, String loginPassword) {
        User user = new User();
        user.setLoginId(loginId);
        user.setLoginPassword(loginPassword);

        logger.info("id: {}, password: {}", loginId, loginPassword);

        userRepository.save(user);
    }
}
