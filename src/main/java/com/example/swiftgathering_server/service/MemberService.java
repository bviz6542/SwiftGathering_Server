package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.dto.MyInfoDto;
import com.example.swiftgathering_server.dto.RegisterDto;
import com.example.swiftgathering_server.dto.ResignDto;
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

    public Long register(RegisterDto registerDto) {
        memberRepository.findByLoginId(registerDto.getLoginId())
                .ifPresent(m -> {
                    throw new EntityExistsException("Login ID already in use: " + registerDto.getLoginId());
                });

        Member member = Member.builder()
                .loginId(registerDto.getLoginId())
                .loginPassword(registerDto.getLoginPassword())
                .name(registerDto.getName())
                .build();
        return memberRepository.save(member);
    }

    public void resign(ResignDto resignDto) {
        Member member = memberRepository.findByIdAndPassword(resignDto.getLoginId(), resignDto.getLoginPassword())
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        memberRepository.remove(member);
    }

    public MyInfoDto verify(LoginDto loginDto) {
        Long memberId = memberRepository
                .findByIdAndPassword(loginDto.getLoginId(), loginDto.getLoginPassword())
                .map(Member::getId)
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        return new MyInfoDto(memberId);
    }
}