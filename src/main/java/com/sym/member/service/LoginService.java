package com.sym.member.service;

import com.sym.member.domain.Member;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.exception.PasswordNotCorrectException;
import com.sym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.sym.member.service.SessionConst.LOGIN_MEMBER;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    public void login(Long id) {
        httpSession.setAttribute(LOGIN_MEMBER, id);
    }
    public void logout() {
        httpSession.removeAttribute(LOGIN_MEMBER);
    }
}
