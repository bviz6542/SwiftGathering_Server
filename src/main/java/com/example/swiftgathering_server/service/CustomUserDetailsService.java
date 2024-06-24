package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.CustomUserDetails;
import com.example.swiftgathering_server.exception.AuthenticationException;
import com.example.swiftgathering_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginUsername(username)
                .orElseThrow(() -> new AuthenticationException("Invalid login ID or password."));
        return new CustomUserDetails(member);
    }
}
