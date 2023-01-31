package com.sym.member.domain;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private int point;
    @Embedded
    private CommonMemberField commonMemberField;

    @Builder
    public Member(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
    public void setCommonMemberField(CommonMemberField commonMemberField) {
        this.commonMemberField = commonMemberField;
    }
}
