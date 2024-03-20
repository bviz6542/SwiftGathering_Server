package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.WebSocketHandler;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void register(String loginId, String loginPassword) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setLoginPassword(loginPassword);
        memberRepository.save(member);
    }

    public void verify(String loginId, String loginPassword) {
        memberRepository
                .findByIdAndPassword(loginId, loginPassword)
                .orElseThrow();
    }
}
