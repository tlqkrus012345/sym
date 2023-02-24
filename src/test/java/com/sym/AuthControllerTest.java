package com.sym;

import com.sym.config.SecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)
@WebMvcTest(Void.class)
public class AuthControllerTest {

    private final MockMvc mvc;
    private final PasswordEncoder passwordEncoder;
    public AuthControllerTest(@Autowired MockMvc mvc, @Autowired PasswordEncoder passwordEncoder) {
        this.mvc = mvc;
        this.passwordEncoder = passwordEncoder;
    }


    @DisplayName("get요청하면 로그인 페이지를 정상 호출한다")
    @Test
    public void login_view() throws Exception {

        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode() {
        String password = "12345";

        String encodedPassword = passwordEncoder.encode(password);

        assertTrue(encodedPassword != null && !encodedPassword.isEmpty());
        assertTrue(passwordEncoder.matches(password, encodedPassword));
        assertTrue(!encodedPassword.equals(password));
    }
}