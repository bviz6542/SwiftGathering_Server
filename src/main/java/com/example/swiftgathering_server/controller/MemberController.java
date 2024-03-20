package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.LoginDto;
import com.example.swiftgathering_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping ("/members")
    public ResponseEntity<Void> register(@RequestBody LoginDto loginDto) {
        String id = loginDto.getId();
        String password = loginDto.getPassword();
        memberService.register(id, password);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        String id = loginDto.getId();
        String password = loginDto.getPassword();
        try {
            memberService.verify(id, password);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .build();
        }
    }
}
