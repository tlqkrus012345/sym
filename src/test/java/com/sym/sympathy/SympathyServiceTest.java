package com.sym.sympathy;

import com.sym.member.dto.MemberDto;
import com.sym.sympathy.domain.PsychologicalSurvey;
import com.sym.sympathy.dto.SurveyRequestDto;
import com.sym.sympathy.repository.SympathyRepository;
import com.sym.sympathy.service.SympathyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SympathyServiceTest {

    @Mock
    private SympathyRepository sympathyRepository;

    @InjectMocks
    private SympathyService sympathyService;

    @Test
    void testSave() {
        SurveyRequestDto requestDto = new SurveyRequestDto();
        requestDto.setOneDepressionQ(2);
        requestDto.setTwoDepressionQ(4);
        requestDto.setOneAngryQ(3);
        requestDto.setTwoAngryQ(1);
        requestDto.setOneInsanityQ(1);
        requestDto.setTwoInsanityQ(2);
        MemberDto memberDto = new MemberDto();
        memberDto.setNickName("Spring");
        memberDto.setEmail("Spring@com");
        requestDto.setMemberDto(memberDto);

        PsychologicalSurvey expectedSurvey = PsychologicalSurvey.of(memberDto.toEntity(),
                "Need Talk and Rest", "N", "N", "B");

        when(sympathyRepository.save(any(PsychologicalSurvey.class))).thenReturn(expectedSurvey);

        sympathyService.save(requestDto);

        verify(sympathyRepository, times(1)).save(any(PsychologicalSurvey.class));
    }

    @Test
    void testDiagnose() {
        SurveyRequestDto requestDto = new SurveyRequestDto();
        requestDto.setOneDepressionQ(2);
        requestDto.setTwoDepressionQ(4);
        requestDto.setOneAngryQ(3);
        requestDto.setTwoAngryQ(1);
        requestDto.setOneInsanityQ(1);
        requestDto.setTwoInsanityQ(2);

        String expectedDiagnosis = "NNG";

        String actualDiagnosis = sympathyService.diagnose(requestDto);

        Assertions.assertEquals(expectedDiagnosis, actualDiagnosis);
    }

    @Test
    void testResultDiagnose() {
        int point = 6;

        String expectedResult = "N";

        String actualResult = sympathyService.resultDiagnose(point);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFinalDiagnose() {
        String result = "NNB";

        String expectedSurveyResult = "Need Serious Talk";

        String actualSurveyResult = sympathyService.finalDiagnose(result);

        Assertions.assertEquals(expectedSurveyResult, actualSurveyResult);
    }
}
