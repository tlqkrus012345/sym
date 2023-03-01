package com.sym.sympathy.domain;

import com.sym.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "survey")
@Entity
public class PsychologicalSurvey {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;
    @OneToOne(mappedBy = "survey", fetch = FetchType.LAZY)
    private Member member;
    private String surveyResult;
    private String depression;
    private String angerControlDisorder;
    private String insanity;

    @CreatedDate
    private LocalDateTime surveyDateTime;
    @LastModifiedDate
    private LocalDateTime modifyDateTime;

    private PsychologicalSurvey(Member member,String surveyResult, String depression, String angerControlDisorder, String insanity) {
        this.depression = depression;
        this.angerControlDisorder = angerControlDisorder;
        this.insanity = insanity;
        this.member = member;
    }
    public static PsychologicalSurvey of(Member member, String surveyResult, String depression, String angerControlDisorder, String insanity) {
        return new PsychologicalSurvey(member, surveyResult, depression, angerControlDisorder, insanity);
    }
}
