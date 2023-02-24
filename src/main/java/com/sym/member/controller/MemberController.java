package com.sym.member.controller;

import com.sym.member.dto.MemberLoginFormDto;
import com.sym.member.service.LoginService;
import com.sym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginFormDto loginDto) {
        String email = loginDto.getEmail();
        boolean existMember = memberService.existsByEmail(email);

        if (existMember) {
            loginService.login(memberService.findByEmail(email).getId());
            return ResponseEntity.ok("로그인에 성공");
        } else {
            return ResponseEntity.ok("로그인 실패");
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        loginService.logout();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
