package com.sym.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    void 회원가입() {
        MemberRegisterRequestDto dto = MemberRegisterRequestDto.builder()
            .email("spring@")
            .nickName("spring")
            .password("123")
            .build();

        Member saveMember = memberRepository.save(dto.toEntity());
        Member findMember = memberService.findMemberById(saveMember.getId());

        assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
    }
}