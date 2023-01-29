package com.sym.member;

import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.exception.MemberRegisterException;
import com.sym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void registerMember(MemberRegisterRequestDto memberDto) {
        if (existsByEmail(memberDto.getEmail())) {
            throw new MemberRegisterException("이미 존재하는 이메일 입니다.");
        }
        memberRepository.save(memberDto.toEntity());
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}