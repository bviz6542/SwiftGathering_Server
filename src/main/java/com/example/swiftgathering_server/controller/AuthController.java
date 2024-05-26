package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.dto.MyInfoDto;
import com.example.swiftgathering_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MyInfoDto> login(@RequestBody LoginDto loginDto) {
        Long memberId = memberService.verify(loginDto.getLoginId(), loginDto.getLoginPassword());
        MyInfoDto myInfoDTO = new MyInfoDto(memberId);
        return ResponseEntity.ok(myInfoDTO);
    }
}