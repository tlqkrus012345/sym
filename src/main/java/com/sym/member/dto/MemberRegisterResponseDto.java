package com.sym.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterResponseDto {

    private String email;
    private String nickName;
    private String password;

    public static MemberRegisterResponseDto from(MemberRegisterRequestDto requestDto) {
        return new MemberRegisterResponseDto(
                requestDto.getEmail(),
                requestDto.getNickName(),
                requestDto.getPassword()
        );
    }
}
