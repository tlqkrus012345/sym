package com.sym.sympathy.controller;

import com.sym.member.domain.Role;
import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.dto.MemberRegisterResponseDto;
import com.sym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sympathy")
public class SympathyController {
    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid MemberRegisterRequestDto requestDto) {
        Role role = Role.Counselor;
        boolean existEmail = memberService.existsByEmail(requestDto.getEmail());

        if (existEmail) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일");
        }
        memberService.registerMember(requestDto, role);
        MemberRegisterResponseDto responseDto = MemberRegisterResponseDto.from(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
