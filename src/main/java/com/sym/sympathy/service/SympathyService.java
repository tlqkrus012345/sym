package com.sym.sympathy.service;

import com.sym.member.domain.Member;
import com.sym.member.service.MemberService;
import com.sym.sympathy.domain.PsychologicalSurvey;
import com.sym.sympathy.dto.SurveyRequestDto;
import com.sym.sympathy.repository.SympathyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SympathyService {

    private final MemberService memberService;
    private final SympathyRepository sympathyRepository;
    @Transactional
    public void save(SurveyRequestDto requestDto) {
        String result = diagnose(requestDto);
        String surveyResult = finalDiagnose(result);

        sympathyRepository.save(toEntity(result, surveyResult, requestDto.getMemberDto().toEntity()));
    }
    public String diagnose(SurveyRequestDto requestDto) {
        int depression = 0;
        int angry = 0;
        int insanity = 0;
        String result = "";

        depression = requestDto.getOneDepressionQ() + requestDto.getTwoDepressionQ();
        angry = requestDto.getOneAngryQ() + requestDto.getTwoAngryQ();
        insanity = requestDto.getOneInsanityQ() + requestDto.getTwoInsanityQ();

        result += resultDiagnose(depression);
        result += resultDiagnose(angry);
        result += resultDiagnose(insanity);

        return result;
    }
    public String resultDiagnose(int point) {
        if (point >= 7) {
            return "B";
        } else if (point >= 4) {
            return "N";
        } else {
            return "G";
        }
    }
    public String finalDiagnose(String result) {
        int B = 0;
        int N = 0;
        int G = 0;

        for (char c : result.toCharArray()) {
            if (c == 'B') {
                B++;
            }
            if (c == 'N') {
                N++;
            }
            if (c == 'G') {
                G++;
            }
        }

        if (B >= 2) return "Need Doctor";
        if (N == 2 && B == 1) return "Need Serious Talk";
        if (N == 2 && G == 1) return "Need Talk";
        if (G == 2 && N == 1) return "Need Rest";
        if (G == 2 && B == 1) return "Need Deep Rest";
        if (N ==1 && B == 1 && G == 1) return "Need Talk and Rest";
        else return "Happy";
    }
    public PsychologicalSurvey toEntity(String result, String surveyResult, Member member) {
        return PsychologicalSurvey.of(
                member,
                surveyResult,
                result.substring(0,1),
                result.substring(1,2),
                result.substring(2)
        );
    }
}
