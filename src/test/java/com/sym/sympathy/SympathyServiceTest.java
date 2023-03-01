package com.sym.sympathy;

import com.sym.member.domain.*;
import com.sym.member.dto.MemberDto;
import com.sym.member.dto.MemberRegisterRequestDto;
import com.sym.member.service.MemberService;
import com.sym.sympathy.domain.PsychologicalSurvey;
import com.sym.sympathy.dto.SurveyRequestDto;
import com.sym.sympathy.repository.SympathyRepository;
import com.sym.sympathy.service.SympathyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SympathyServiceTest {
    @Mock
    private MemberService memberService;
    @Mock
    private SympathyRepository sympathyRepository;
    @InjectMocks
    private SympathyService sympathyService;
    private List<Member> counselorList;
    private Member member;
    private MemberRegisterRequestDto memberDto;
    @BeforeEach
    void setUp() {
        Address address = new Address("St", "City", "Zip");
        CommonMemberField commonMemberField = new CommonMemberField("mysql", Gender.MAN, "mysql", address);

        memberDto = new MemberRegisterRequestDto("a","b","c");
        member = memberDto.toEntity();
        member.setRole(Role.Member);
        member.setCommonMemberField(commonMemberField);
        ReflectionTestUtils.setField(member, "id", 1L);

        counselorList = new ArrayList<>();

        Member counselor1 = new Member("a","b","c");
        CommonMemberField commonMemberField1 = new CommonMemberField("Counselor", Gender.MAN, "c1@example.com", address);
        counselor1.setCommonMemberField(commonMemberField1);
        counselor1.setRole(Role.Counselor);
        ReflectionTestUtils.setField(counselor1, "id", 2L);
        counselorList.add(counselor1);

        Member counselor2 = new Member("d","e","f");
        CommonMemberField commonMemberField2 = new CommonMemberField("Counselor", Gender.WOMAN, "c2@example.com", address);
        counselor2.setCommonMemberField(commonMemberField2);
        counselor2.setRole(Role.Counselor);
        ReflectionTestUtils.setField(counselor2, "id", 3L);
        counselorList.add(counselor2);
    }
    @Test
    void testMatchCounselor() {
        SurveyRequestDto requestDto = new SurveyRequestDto();
        Address address = new Address("a","City","b");
        requestDto.setMemberDto(new MemberDto(member));
        requestDto.getMemberDto().toEntity().setCommonMemberField(new CommonMemberField("a",null,null,address));
        sympathyService.toEntity("BBB","Need Counselor", member);

        // mock the behavior of the memberService
        when(memberService.findMembers()).thenReturn(counselorList);

        // act
        Long counselorId = sympathyService.matchCounselor("Need Counselor", member);

        // assert
        assertEquals(2L, counselorId.longValue());
    }
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

        assertEquals(expectedDiagnosis, actualDiagnosis);
    }

    @Test
    void testResultDiagnose() {
        int point = 6;

        String expectedResult = "N";

        String actualResult = sympathyService.resultDiagnose(point);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testFinalDiagnose() {
        String result = "NNB";

        String expectedSurveyResult = "Need Serious Talk";

        String actualSurveyResult = sympathyService.finalDiagnose(result);

        assertEquals(expectedSurveyResult, actualSurveyResult);
    }
}
