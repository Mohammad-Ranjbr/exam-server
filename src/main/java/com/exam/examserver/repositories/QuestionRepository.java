package com.exam.examserver.repositories;

import com.exam.examserver.entities.Question;
import com.exam.examserver.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    List<Question> getByQuiz(Quiz quiz);

}
