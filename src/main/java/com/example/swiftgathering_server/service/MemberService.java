package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.LoginInputDto;
import com.example.swiftgathering_server.dto.MyInfoDto;
import com.example.swiftgathering_server.dto.RegisterDto;
import com.example.swiftgathering_server.dto.ResignDto;
import com.example.swiftgathering_server.repository.MemberRepository;
import com.example.swiftgathering_server.exception.AuthenticationException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long register(RegisterDto registerDto) {
        memberRepository.findByLoginId(registerDto.getLoginId())
                .ifPresent(m -> {
                    throw new EntityExistsException("Login ID already in use: " + registerDto.getLoginId());
                });

        String encodedPassword = bCryptPasswordEncoder.encode(registerDto.getLoginPassword());
        Member member = Member.builder()
                .loginId(registerDto.getLoginId())
                .loginPassword(encodedPassword)
                .name(registerDto.getName())
                .build();
        return memberRepository.save(member);
    }

    public void resign(ResignDto resignDto) {
        Member member = memberRepository.findByLoginId(resignDto.getLoginId())
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        if (!bCryptPasswordEncoder.matches(resignDto.getLoginPassword(), member.getLoginPassword())) {
            throw new AuthenticationException("Invalid login ID or password.");
        }
        memberRepository.remove(member);
    }

    public MyInfoDto verify(LoginInputDto loginDto) {
        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        if (!bCryptPasswordEncoder.matches(loginDto.getLoginPassword(), member.getLoginPassword())) {
            throw new AuthenticationException("Invalid login ID or password.");
        }
        return new MyInfoDto(member.getId());
    }
}