package com.sym.member.dto;

import com.sym.member.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateResponseDto {

    private String nickName;
    private String password;
    private String koreanName;
    private String phone;
    private Gender gender;
    private String country;
    private String city;
    private String zipcode;

    public static MemberUpdateResponseDto from(MemberUpdateRequestDto requestDto) {
        return new MemberUpdateResponseDto(
                requestDto.getNickName(),
                requestDto.getPassword(),
                requestDto.getKoreanName(),
                requestDto.getPhone(),
                requestDto.getGender(),
                requestDto.getCountry(),
                requestDto.getCity(),
                requestDto.getZipcode()
        );
    }
}
