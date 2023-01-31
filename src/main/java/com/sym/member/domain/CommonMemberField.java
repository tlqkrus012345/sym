package com.sym.member.domain;

import com.sym.member.domain.Address;
import com.sym.member.domain.Gender;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class CommonMemberField {

    private String koreanName;
    private Gender gender;
    private String phone;
    @Embedded
    private Address address;

    protected CommonMemberField() {

    }
    public CommonMemberField(String koreanName, Gender gender, String phone, Address address) {
        this.koreanName = koreanName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }
}
