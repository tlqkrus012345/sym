package com.sym.member.dto;

import com.sym.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberRegisterRequestDto {

    private Long id;
    private String email;
    private String nickName;
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
}
