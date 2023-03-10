package com.sym.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sym.member.domain.Member;
import com.sym.member.domain.Role;
import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.exception.MemberRegisterException;
import com.sym.member.exception.PointNotEnoughException;
import com.sym.member.repository.MemberRepository;
import java.util.Optional;

import com.sym.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private MemberRegisterRequestDto memberDto;
    private Member member;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
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
        doReturn("encodedPassword").when(passwordEncoder).encode(member.getPassword());
        Role role = Role.Member;

        memberService.registerMember(memberDto,role);

        verify(memberRepository, times(1)).save(any(Member.class));
    }
    @Test
    @DisplayName("중복된 이메일로 인하여 회원가입 실패 테스트")
    void register_x() {
        doReturn(true).when(memberRepository)
            .existsByEmail(memberDto.getEmail());
        Role role = Role.Member;

        assertThatThrownBy(()-> memberService.registerMember(memberDto,role))
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

    @Test
    @DisplayName("포인트를 충전하면 회원의 포인트가 충전이된다.")
    void point_chargePoint_updatePoint() {
        int point = 500;

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        memberService.chargePoint(point, member.getId());

        verify(memberRepository, times(1)).findById(member.getId());
        assertThat(member.getPoint()).isEqualTo(point);

    }

    @Test
    @DisplayName("포인트를 사용하면 보유하고 있는 포인트가 줄어든다")
    void point_usePoint_updatePoint() {
        int point = 500;

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        memberService.chargePoint(2000, member.getId());

        memberService.usePoint(point, member.getId());

        assertThat(member.getPoint()).isEqualTo(1500);
    }

    @Test
    @DisplayName("포인트가 부족한 상태에서 사용하면 예외가 발생한다")
    void point_usePoint_exception() {
        int point = 500;

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        assertThatThrownBy(()-> memberService.usePoint(point, member.getId()))
                .isInstanceOf(PointNotEnoughException.class);
    }
}