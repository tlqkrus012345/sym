package com.sym.sympathy.dto;

import com.sym.member.dto.MemberDto;
import com.sym.sympathy.domain.PsychologicalSurvey;
import lombok.Data;

@Data
public class SurveyRequestDto {
    private Long id;
    private MemberDto memberDto;
    private int oneDepressionQ;
    private int twoDepressionQ;
    private int oneAngryQ;
    private int twoAngryQ;
    private int oneInsanityQ;
    private int twoInsanityQ;

}
