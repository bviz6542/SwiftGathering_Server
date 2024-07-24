package com.example.swiftgathering_server;

import com.example.swiftgathering_server.dto.LoginInputDto;
import com.example.swiftgathering_server.dto.LoginOutputDto;
import com.example.swiftgathering_server.dto.RegisterDto;
import com.example.swiftgathering_server.service.AuthService;
import com.example.swiftgathering_server.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class LoginTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @Autowired
    private EntityManager em;

    @Test
    public void 등록하고로그인() {
        // given
        RegisterDto registerDto0 = new RegisterDto("testGuest", "testGuest", "testGuest");
        Long member0Id = memberService.register(registerDto0);

        // when
        LoginInputDto loginInputDto = new LoginInputDto("testGuest", "testGuest");
        LoginOutputDto loginOutputDto = authService.authenticateAndGenerateToken(loginInputDto);

        // then
        Assertions.assertEquals(member0Id, loginOutputDto.getMemberId());
    }
}