package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.MyInfoDTO;
import com.example.swiftgathering_server.dto.RegisterDto;
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
    public ResponseEntity<Long> register(@RequestBody RegisterDto registerDto) {
        Long memberId = memberService.register(
                registerDto.getLoginId(),
                registerDto.getLoginPassword(),
                registerDto.getName()
        );
        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> resign(@RequestBody ResignDto resignDto) {
        memberService.resign(resignDto.getLoginId(), resignDto.getLoginPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<MyInfoDTO> login(@RequestBody LoginDto loginDto) {
        Long memberId = memberService.verify(loginDto.getLoginId(), loginDto.getLoginPassword());
        MyInfoDTO myInfoDTO = new MyInfoDTO(memberId);
        return ResponseEntity.ok(myInfoDTO);
    }
}
