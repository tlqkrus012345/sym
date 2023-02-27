package com.sym.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sym.member.controller.MemberController;
import com.sym.member.domain.CommonMemberField;
import com.sym.member.domain.Member;
import com.sym.member.dto.*;
import com.sym.member.repository.MemberRepository;
import com.sym.member.service.LoginService;
import com.sym.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .build();
    }

    @Test
    public void testRegisterMember() throws Exception {

        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test@test.com", "test1234", "nickname");
        MemberRegisterResponseDto responseDto = MemberRegisterResponseDto.from(requestDto);

        when(memberService.existsByEmail(requestDto.getEmail())).thenReturn(false);
        doNothing().when(memberService).registerMember(any(MemberRegisterRequestDto.class));


        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))


                .andExpect(status().isCreated())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDto)));
    }
    @Test
    public void testDeleteMember() throws Exception {

        Long id = 1L;
        doNothing().when(memberRepository).deleteById(id);

        mockMvc.perform(delete("/api/members/{id}", id))
                .andExpect(status().isNoContent());
    }
}