package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.dto.CustomUserDetails;
import com.example.swiftgathering_server.dto.LoginInputDto;
import com.example.swiftgathering_server.dto.LoginOutputDto;
import com.example.swiftgathering_server.jwt.JwtUtil;
import com.example.swiftgathering_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginOutputDto authenticateAndGenerateToken(LoginInputDto loginInputDto) {
        try {
            String username = loginInputDto.getLoginId();
            String password = loginInputDto.getLoginPassword();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Role not found"))
                    .getAuthority();
            String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);

            Long memberId = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new RuntimeException("Invalid login ID or password."))
                    .getId();

            return new LoginOutputDto(token, memberId);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
