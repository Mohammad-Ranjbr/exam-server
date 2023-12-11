package com.exam.examserver.services.implementation;

import com.exam.examserver.entities.Quiz;
import com.exam.examserver.exception.ResourceFoundException;
import com.exam.examserver.exception.ResourceNotFoundException;
import com.exam.examserver.payloads.CategoryDto;
import com.exam.examserver.payloads.QuizDto;
import com.exam.examserver.repositories.QuizRepository;
import com.exam.examserver.services.CategoryService;
import com.exam.examserver.services.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;
    private final CategoryService categoryService;

    @Autowired
    public QuizServiceImpl(ModelMapper modelMapper , QuizRepository quizRepository,CategoryService categoryService){
        this.modelMapper = modelMapper;
        this.quizRepository = quizRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public QuizDto createQuiz(QuizDto quizDto) {
        if (quizRepository.findByTitle(quizDto.getTitle()).isPresent()){
            System.out.println("Quiz Already Exists");
            throw new ResourceFoundException("Quiz","Title",String.valueOf(quizDto.getTitle()));
        }
        Quiz quiz = this.quizDtoToQuiz(quizDto);
        Quiz savedQuiz = quizRepository.save(quiz);
        return this.quizToQuizDto(savedQuiz);
    }

    @Override
    @Transactional
    public QuizDto updateQuiz(QuizDto quizDto, int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz","ID",String.valueOf(quizDto)));
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setMaxMarks(quizDto.getMaxMarks());
        quiz.setNumberOfQuestions(quizDto.getNumberOfQuestions());
        quiz.setActive(quizDto.isActive());
        Quiz savedQuiz = quizRepository.save(quiz);
        return this.quizToQuizDto(savedQuiz);
    }

    @Override
    public List<QuizDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(this::quizToQuizDto).collect(Collectors.toList());
    }

    @Override
    public QuizDto getQuiz(int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz","ID",String.valueOf(quizId)));
        return this.quizToQuizDto(quiz);
    }

    @Override
    @Transactional
    public void deleteQuiz(int quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz","ID",String.valueOf(quizId)));
        quizRepository.delete(quiz);
    }

    @Override
    public Quiz quizDtoToQuiz(QuizDto quizDto) {
        return this.modelMapper.map(quizDto,Quiz.class);
    }

    @Override
    public QuizDto quizToQuizDto(Quiz quiz) {
        return this.modelMapper.map(quiz,QuizDto.class);
    }

    @Override
    public List<QuizDto> getQuizzesOfCategory(int categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        List<Quiz> quizzes = quizRepository.findByCategory(categoryService.categoryDtoToCategory(categoryDto));
        return quizzes.stream().map(this::quizToQuizDto).collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> getActiveQuizzes() {
        List<Quiz> quizzes = quizRepository.findByActive(true);
        return quizzes.stream().map(this::quizToQuizDto).collect(Collectors.toList());
    }

    @Override
    public List<QuizDto> getActiveQuizzesOfCategory(int categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        List<Quiz> quizzes = quizRepository.findByCategoryAndActive(this.categoryService.categoryDtoToCategory(categoryDto),true);
        return quizzes.stream().map(this::quizToQuizDto).collect(Collectors.toList());
    }

}
