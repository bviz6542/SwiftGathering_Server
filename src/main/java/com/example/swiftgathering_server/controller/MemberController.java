package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.ResignDto;
import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping ("/members")
    public ResponseEntity<Long> register(@RequestBody LoginDto loginDto) {
        Long memberId = memberService.register(loginDto.getId(), loginDto.getPassword());
        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> resign(@RequestBody ResignDto resignDto) {
        memberService.resign(resignDto.getId(), resignDto.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        memberService.verify(loginDto.getId(), loginDto.getPassword());
        return ResponseEntity.ok().build();
    }
}
