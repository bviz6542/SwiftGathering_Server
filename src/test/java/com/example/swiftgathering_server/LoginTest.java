package com.example.swiftgathering_server;

import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class LoginTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

//    @Test
//    public void 등록하고로그인() {
//        // given
//        String loginId = "testGuest";
//        String loginPassword = "testGuest";
//        String name = "testGuest";
//        memberRepository.findByLoginId(loginId)
//                .ifPresent(m -> {
//                    throw new EntityExistsException("Login ID already in use: " + loginId);
//                });
//        Member member = Member.builder()
//                .loginId(loginId)
//                .loginPassword(loginPassword)
//                .name(name)
//                .build();
//        memberRepository.save(member);
//        em.flush();
//
//        // when
//        Optional<Member> loggedInMember = memberRepository.findByIdAndPassword(loginId, loginPassword);
//
//        // then
//        Assertions.assertNotNull(loggedInMember);
//        loggedInMember.ifPresent((m -> {
//            System.out.println(loggedInMember.get().getLoginId());
//        }));
//    }
}