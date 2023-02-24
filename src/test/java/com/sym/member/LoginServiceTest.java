package com.sym.member;

import com.sym.member.domain.Member;
import com.sym.member.exception.MemberNotFoundException;
import com.sym.member.service.LoginService;
import com.sym.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.sym.member.service.SessionConst.LOGIN_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private MockHttpSession mockHttpSession;
    private Member member;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private LoginService loginService;
    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email("spring@java.com")
                .password("1234")
                .nickName("mysql")
                .build();

        // @Mock, @Spy, @Captor, @InjectMocks 가 붙은 테스트 클래스의 필드들을 모두 초기화한다.
        // mock을 사용하기 전에 실행한다.
        MockitoAnnotations.openMocks(this);
        mockHttpSession = new MockHttpSession();

        loginService = new LoginService(mockHttpSession, memberService);
    }

    @Test
    @DisplayName("세션에 로그인 성공한 아이디를 저장한다")
    void login_o_saveInSession() {
        Long memberId = 1L;
        loginService.login(memberId);

        assertThat(mockHttpSession.getAttribute(LOGIN_MEMBER)).isNotNull();
        assertThat(mockHttpSession.getAttribute(LOGIN_MEMBER)).isEqualTo(1L);
    }
    @Test
    @DisplayName("로그아웃을 하면 세션에 저장된 아이디가 삭제된다")
    void logout_o_deleteSessionId() {
        mockHttpSession.setAttribute(LOGIN_MEMBER, 1L);

        loginService.logout();

        assertThat(mockHttpSession.getAttribute(LOGIN_MEMBER)).isNull();
    }
}
