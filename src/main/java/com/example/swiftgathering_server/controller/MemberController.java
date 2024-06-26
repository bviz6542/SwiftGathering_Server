package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.RegisterDto;
import com.example.swiftgathering_server.dto.ResignDto;
import com.example.swiftgathering_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody RegisterDto registerDto) {
        Long memberId = memberService.register(registerDto);
        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping
    public ResponseEntity<Void> resign(@RequestBody ResignDto resignDto) {
        memberService.resign(resignDto);
        return ResponseEntity.noContent().build();
    }
}