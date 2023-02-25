package com.sym.member.controller;

import com.sym.member.domain.CommonMemberField;
import com.sym.member.dto.*;
import com.sym.member.repository.MemberRepository;
import com.sym.member.service.LoginService;
import com.sym.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LoginService loginService;
    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid MemberRegisterRequestDto requestDto) {
        boolean existEmail = memberService.existsByEmail(requestDto.getEmail());
        if (existEmail) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일");
        }
        memberService.registerMember(requestDto);
        MemberRegisterResponseDto responseDto = MemberRegisterResponseDto.from(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid MemberUpdateRequestDto requestDto) {
        CommonMemberField commonMemberField = MemberUpdateRequestDto.setCommonMemberField(requestDto);

        memberService.updateMember(id, requestDto, commonMemberField);
        MemberUpdateResponseDto responseDto = MemberUpdateResponseDto.from(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            memberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginFormDto loginDto) {
        String email = loginDto.getEmail();
        boolean existMember = memberService.existsByEmail(email);

        if (existMember) {
            loginService.login(memberService.findByEmail(email).getId());
            return ResponseEntity.ok("로그인에 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        loginService.logout();
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/point/{id}")
    public void point(@PathVariable Long id, @RequestBody @Valid MemberPointRequestDto requestDto) {
        memberService.chargePoint(requestDto.getPoint(), id);
    }
}
