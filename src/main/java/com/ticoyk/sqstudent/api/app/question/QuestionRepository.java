package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.category.Category;
import com.ticoyk.sqstudent.api.auth.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByUser(Pageable pageable, User user);
    Page<Question> findAllByCategory(Pageable pageable, Category category);
    Page<Question> findAllByIsChallenge(Pageable pageable, Boolean isChallenge);
}
