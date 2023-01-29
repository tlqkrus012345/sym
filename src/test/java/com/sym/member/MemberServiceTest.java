package com.sym.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.exception.MemberRegisterException;
import com.sym.member.repository.MemberRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private MemberRegisterRequestDto memberDto;
    private Member member;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberDto = MemberRegisterRequestDto.builder()
            .email("spring@sym.com")
            .nickName("spring")
            .password("123")
            .build();

        member = memberDto.toEntity();
        ReflectionTestUtils.setField(member, "id", 1L);
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void register_o() {
        doReturn(false).when(memberRepository)
                .existsByEmail(memberDto.getEmail());
        doReturn(member).when(memberRepository).save(any(Member.class));

        memberService.registerMember(memberDto);
        verify(memberRepository, times(1)).save(any(Member.class));
    }
    @Test
    @DisplayName("중복된 이메일로 인하여 회원가입 실패 테스트")
    void register_x() {
        doReturn(true).when(memberRepository)
            .existsByEmail(memberDto.getEmail());

        assertThatThrownBy(()-> memberService.registerMember(memberDto))
            .isInstanceOf(MemberRegisterException.class);
    }
    @Test
    @DisplayName("중복된 이메일 존재하는 테스트")
    void exist_email_o() {
        given(memberRepository.existsByEmail(any())).willReturn(true);

        assertThat(memberService.existsByEmail(member.getEmail())).isTrue();
    }
    @Test
    @DisplayName("중복된 이메일 존재하지 않는 테스트")
    void exist_email_x() {
        given(memberRepository.existsByEmail(any())).willReturn(false);

        assertThat(memberService.existsByEmail(member.getEmail())).isFalse();
    }

    @Test
    @DisplayName("존재하는 회원 이메일 검색 테스트")
    void find_email_o() {
        given(memberRepository.findByEmail(memberDto.getEmail())).willReturn(Optional.of(member));

        assertThat(memberService.findByEmail(member.getEmail())).isEqualTo(member);
    }
    @Test
    @DisplayName("존재하지 않는 회원 이메일 검색 테스트")
    void find_email_x() {
        given(memberRepository.findByEmail(memberDto.getEmail())).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findByEmail(memberDto.getEmail()))
            .isInstanceOf(MemberNotFoundException.class);
    }
}