package com.exam.examserver.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizDto {

    private Integer quizId;
    private String title;
    private String description;
    private String maxMarks;
    private String numberOfQuestions;
    private boolean active;
    private CategoryDto category; // recursive type is not important name of variable is important should be equals to entity variables name

}
