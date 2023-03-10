package com.sym.member.service;

import com.sym.member.domain.CommonMemberField;
import com.sym.member.domain.Member;
import com.sym.member.domain.Role;
import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.dto.MemberUpdateRequestDto;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.exception.MemberRegisterException;
import com.sym.member.exception.PasswordNotCorrectException;
import com.sym.member.exception.PointNotEnoughException;
import com.sym.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void registerMember(MemberRegisterRequestDto memberDto, Role role) {
        if (existsByEmail(memberDto.getEmail())) {
            throw new MemberRegisterException("이미 존재하는 이메일 입니다.");
        }
        Member member = memberDto.toEntity();
        member.setRole(role);
        String password = passwordEncoder.encode(member.getPassword());
        member.encodePassword(password);

        memberRepository.save(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequestDto requestDto, CommonMemberField commonMemberField) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.setCommonMemberField(commonMemberField);
        if (requestDto.getPassword() != null) {
            member.updatePassword(requestDto.getPassword());
        }
        if (requestDto.getNickName() != null) {
            member.updateNickName(requestDto.getNickName());
        }
    }
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public boolean existsPassword(Member member, String password) {
        if (passwordEncoder.matches(password, member.getPassword())) {
            return true;
        } else {
            throw new PasswordNotCorrectException();
        }
    }
    @Transactional
    public void chargePoint(int point, Long id) {
        Member member = findById(id);
        member.chargePoint(point);
    }
    @Transactional
    public void usePoint(int point, Long id) {
        Member member = findById(id);
        if (member.getPoint() - point < 0) {
            throw new PointNotEnoughException("포인트가 부족합니다.");
        } else {
            member.usePoint(point);
        }
    }

}
