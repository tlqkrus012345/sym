package com.sym.member.dto;

import com.sym.member.domain.Member;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequestDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String password;

    @Builder
    public MemberRegisterRequestDto(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
    public Member toEntity() {
        return Member.builder()
            .email(email)
            .nickName(nickName)
            .password(password)
            .build();
    }
    public MemberDto toMemberDto(MemberRegisterRequestDto dto) {
        return new MemberDto(dto.toEntity());
    }
}
