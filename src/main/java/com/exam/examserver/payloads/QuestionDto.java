package com.exam.examserver.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto {

    private Integer questionId;
    private String content;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @JsonIgnore
    private String answer;
    private String givenAnswer;
    private QuizDto quiz;

}
