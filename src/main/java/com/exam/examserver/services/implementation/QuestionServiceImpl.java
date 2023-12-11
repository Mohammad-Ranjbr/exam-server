package com.exam.examserver.services.implementation;

import com.exam.examserver.entities.Question;
import com.exam.examserver.exception.ResourceNotFoundException;
import com.exam.examserver.payloads.QuestionDto;
import com.exam.examserver.payloads.QuizDto;
import com.exam.examserver.payloads.QuizResultResponse;
import com.exam.examserver.repositories.QuestionRepository;
import com.exam.examserver.services.QuestionService;
import com.exam.examserver.services.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final ModelMapper modelMapper;
    private final QuizService quizService;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(ModelMapper modelMapper,QuestionRepository questionRepository,QuizService quizService){
        this.modelMapper = modelMapper;
        this.quizService = quizService;
        this.questionRepository = questionRepository;
    }
    @Override
    @Transactional
    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question question = this.questionDtoToQuestion(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return this.questionToQuestionDto(savedQuestion);
    }

    @Override
    @Transactional
    public QuestionDto updateQuestion(QuestionDto questionDto, int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question","ID",String.valueOf(questionId)));
        question.setContent(questionDto.getContent());
        question.setAnswer(questionDto.getAnswer());
        question.setOption1(questionDto.getOption1());
        question.setOption2(questionDto.getOption2());
        question.setOption3(questionDto.getOption3());
        question.setOption4(questionDto.getOption4());
        Question updatedQuestion = questionRepository.save(question);
        return this.questionToQuestionDto(updatedQuestion);
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(this::questionToQuestionDto).collect(Collectors.toList());
    }

    @Override
    public QuestionDto getQuestionDto(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question","ID",String.valueOf(questionId)));
        return this.questionToQuestionDto(question);
    }

    @Override
    public Question getQuestion(int questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question","ID",String.valueOf(questionId)));
    }

    @Override
    @Transactional
    public void deleteQuestion(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question","ID",String.valueOf(questionId)));
        questionRepository.delete(question);
    }

    @Override
    public List<QuestionDto> getQuestionsOfQuiz(int quizID) {
        QuizDto quizDto = quizService.getQuiz(quizID);
        List<Question> questions = questionRepository.getByQuiz(quizService.quizDtoToQuiz(quizDto));
        if(questions.size() > Integer.parseInt(quizDto.getNumberOfQuestions())){
            questions = questions.subList(0,Integer.parseInt(quizDto.getNumberOfQuestions() + 1));
        }
        Collections.shuffle(questions);
        return questions.stream().map(this::questionToQuestionDto).collect(Collectors.toList());
    }

    @Override
    public List<QuestionDto> getQuestionsOfQuizAdmin(int quizID) {
        QuizDto quizDto = quizService.getQuiz(quizID);
        List<Question> questions = questionRepository.getByQuiz(quizService.quizDtoToQuiz(quizDto));
        return questions.stream().map(this::questionToQuestionDto).collect(Collectors.toList());
    }

    @Override
    public Question questionDtoToQuestion(QuestionDto questionDto) {
        return modelMapper.map(questionDto,Question.class);
    }

    @Override
    public QuestionDto questionToQuestionDto(Question question) {
        return modelMapper.map(question,QuestionDto.class);
    }

    @Override
    public QuizResultResponse evaluatingQuiz(List<QuestionDto> questionDtos) {
        double markSingle = Double.parseDouble(questionDtos.get(0).getQuiz().getMaxMarks()) / questionDtos.size();
        AtomicReference<Double> marksGot= new AtomicReference<>((double) 0);
        AtomicInteger correctAnswers= new AtomicInteger();
        AtomicInteger attempted= new AtomicInteger();
        questionDtos.forEach(q -> {
            try {
                Question question = this.getQuestion(q.getQuestionId());
                if (question.getAnswer().trim().equals(q.getGivenAnswer().trim())) {
                    correctAnswers.addAndGet(1);
                    marksGot.updateAndGet(v -> (v + markSingle));
                }
                if (q.getGivenAnswer() != null) {
                    attempted.getAndIncrement();
                }
            } catch (NullPointerException exception) {
                System.out.println();
            }
        });
        System.out.println("Attempted : "+attempted+"\nCorrect Answer : "+correctAnswers+"\nMArks Got : "+marksGot);
        return new QuizResultResponse(marksGot,correctAnswers,attempted);
    }

}
