package com.exam.examserver.controllers;

import com.exam.examserver.payloads.ApiResponse;
import com.exam.examserver.payloads.QuestionDto;
import com.exam.examserver.payloads.QuizResultResponse;
import com.exam.examserver.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api/questions")
@CrossOrigin({"http://localhost:4200","http://localhost:9091"})
public class QuestionController {

    private final QuestionService questionService;


    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @PostMapping("/")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto){
        return new ResponseEntity<>(questionService.createQuestion(questionDto), HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto,@PathVariable("questionId") int question_id){
        return new ResponseEntity<>(questionService.updateQuestion(questionDto,question_id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(){
        return new ResponseEntity<>(questionService.getAllQuestions(),HttpStatus.OK);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable("questionId") int question_id){
        return new ResponseEntity<>(questionService.getQuestionDto(question_id),HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsOfQuiz(@PathVariable("quizId") int quiz_id){
        return new ResponseEntity<>(questionService.getQuestionsOfQuiz(quiz_id),HttpStatus.OK);
    }

    @GetMapping("/quiz/all/{quizId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsOfQuizAdmin(@PathVariable("quizId") int quiz_id){
        return new ResponseEntity<>(questionService.getQuestionsOfQuizAdmin(quiz_id),HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable("questionId") int question_id){
        questionService.deleteQuestion(question_id);
        return new ResponseEntity<>(new ApiResponse(String.format("Question With ID : %d Deleted Successfully",question_id),true),HttpStatus.OK);
    }

    @PostMapping("/eval-quiz")
    public ResponseEntity<QuizResultResponse> evaluatingQuiz(@RequestBody List<QuestionDto> questionDtos){
        QuizResultResponse quizResultResponse = questionService.evaluatingQuiz(questionDtos);
        return new ResponseEntity<>(quizResultResponse,HttpStatus.OK);
    }

}
