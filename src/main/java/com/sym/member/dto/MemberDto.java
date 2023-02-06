package com.sym.member.dto;

import com.sym.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private int point;
    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.password = member.getPassword();
        this.point = member.getPoint();
    }
    public Member toEntity() {
        return new Member(email, nickName, password);
    }
}
