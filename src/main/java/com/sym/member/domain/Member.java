package com.sym.member.domain;

import javax.persistence.*;

import com.sym.sympathy.domain.PsychologicalSurvey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String nickName;
    private String password;
    private int point;
    private Long counselorId;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private PsychologicalSurvey survey;
    @Embedded
    private CommonMemberField commonMemberField;

    @Builder
    public Member(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setCommonMemberField(CommonMemberField commonMemberField) {
        this.commonMemberField = commonMemberField;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }
    public void chargePoint(int point) {
        this.point += point;
    }
    public void usePoint(int point) {
        this.point -= point;
    }
    public void encodePassword(String password) {
        this.password = password;
    }
    public void setCounselorId(Long counselorId){
        this.counselorId = counselorId;
    }
}
