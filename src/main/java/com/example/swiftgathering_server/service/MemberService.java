package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.repository.MemberRepository;
import com.example.swiftgathering_server.exception.AuthenticationException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long register(String loginId, String loginPassword) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(m -> {
                    throw new EntityExistsException("Login ID already in use: " + loginId);
                });

        Member member = Member.builder()
                .loginId(loginId)
                .loginPassword(loginPassword)
                .build();
        return memberRepository.save(member);
    }

    public void resign(String loginId, String loginPassword) {
        Member member = memberRepository.findByIdAndPassword(loginId, loginPassword)
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        memberRepository.remove(member);
    }

    public void verify(String loginId, String loginPassword) {
        memberRepository
                .findByIdAndPassword(loginId, loginPassword)
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
    }
}
