package com.sym.member.dto;

import com.sym.member.Member;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
