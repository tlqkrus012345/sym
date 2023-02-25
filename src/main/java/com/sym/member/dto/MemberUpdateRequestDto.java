package com.sym.member.dto;

import com.sym.member.domain.Address;
import com.sym.member.domain.CommonMemberField;
import com.sym.member.domain.Gender;
import com.sym.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    private String nickName;
    private String password;
    private String koreanName;
    private String phone;
    private Gender gender;
    private String country;
    private String city;
    private String zipcode;

    public static CommonMemberField setCommonMemberField(MemberUpdateRequestDto requestDto) {
        Address address = new Address(requestDto.country, requestDto.city, requestDto.zipcode);
        return new CommonMemberField(requestDto.koreanName, requestDto.gender, requestDto.phone, address);
    }

}
