package com.exam.examserver.controllers;

import com.exam.examserver.payloads.ApiResponse;
import com.exam.examserver.payloads.QuizDto;
import com.exam.examserver.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin({"http://localhost:4200","http://localhost:9091"})
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping("/")
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto){
        return new ResponseEntity<>(quizService.createQuiz(quizDto), HttpStatus.CREATED);
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDto> updateQuiz(@RequestBody QuizDto quizDto,@PathVariable("quizId") int quiz_id){
        return new ResponseEntity<>(quizService.updateQuiz(quizDto,quiz_id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<QuizDto>> getQuizzes(){
        return new ResponseEntity<>(quizService.getAllQuizzes(),HttpStatus.OK);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable("quizId") int quiz_id){
        return new ResponseEntity<>(quizService.getQuiz(quiz_id),HttpStatus.OK);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable("quizId") int quiz_id){
        quizService.deleteQuiz(quiz_id);
        return new ResponseEntity<>(new ApiResponse(String.format("Quiz With ID : %d Deleted Successfully",quiz_id),true),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuizDto>> getQuestionsOfCategory(@PathVariable("categoryId") int category_id){
        return new ResponseEntity<>(quizService.getQuizzesOfCategory(category_id),HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<QuizDto>> getActiveQuizzes(){
        return new ResponseEntity<>(quizService.getActiveQuizzes(),HttpStatus.OK);
    }

    @GetMapping("/active/category/{categoryId}")
    public ResponseEntity<List<QuizDto>> getActiveQuizzesOfCategory(@PathVariable("categoryId") int category_id){
        return new ResponseEntity<>(quizService.getActiveQuizzesOfCategory(category_id),HttpStatus.OK);
    }

}
