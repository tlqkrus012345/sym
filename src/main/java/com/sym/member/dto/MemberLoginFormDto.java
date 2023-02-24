package com.sym.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginFormDto {
    private String email;
    private String password;
}
