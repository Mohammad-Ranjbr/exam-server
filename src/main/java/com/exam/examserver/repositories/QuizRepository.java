package com.exam.examserver.repositories;

import com.exam.examserver.entities.Category;
import com.exam.examserver.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

    Optional<Quiz> findByTitle(String title);
    List<Quiz> findByCategory(Category category);
    List<Quiz> findByActive(boolean active);
    List<Quiz> findByCategoryAndActive(Category category,boolean active);

}
