package com.exam.examserver.services;

import com.exam.examserver.entities.Quiz;
import com.exam.examserver.payloads.QuizDto;

import java.util.List;

public interface QuizService {

    QuizDto createQuiz(QuizDto quiz);
    QuizDto updateQuiz(QuizDto quizDto,int quizId);
    List<QuizDto> getAllQuizzes();
    QuizDto getQuiz(int quizId);
    void deleteQuiz(int quizId);
    Quiz quizDtoToQuiz(QuizDto quizDto);
    QuizDto quizToQuizDto(Quiz quiz);
    List<QuizDto> getQuizzesOfCategory(int categoryId);
    List<QuizDto> getActiveQuizzes();
    List<QuizDto> getActiveQuizzesOfCategory(int categoryId);

}
