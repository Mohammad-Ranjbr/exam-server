package com.exam.examserver.services;

import com.exam.examserver.entities.Question;
import com.exam.examserver.payloads.QuestionDto;
import com.exam.examserver.payloads.QuizDto;
import com.exam.examserver.payloads.QuizResultResponse;

import java.util.List;

public interface QuestionService {

    QuestionDto createQuestion(QuestionDto questionDto);
    QuestionDto updateQuestion(QuestionDto questionDto,int questionId);
    List<QuestionDto> getAllQuestions();
    QuestionDto getQuestionDto(int questionId);
    Question getQuestion(int questionId);
    void deleteQuestion(int questionId);
    Question questionDtoToQuestion(QuestionDto questionDto);
    QuestionDto questionToQuestionDto(Question question);
    List<QuestionDto> getQuestionsOfQuiz(int quizId);
    List<QuestionDto> getQuestionsOfQuizAdmin(int quizID);
    QuizResultResponse evaluatingQuiz(List<QuestionDto> questionDtos);

}
