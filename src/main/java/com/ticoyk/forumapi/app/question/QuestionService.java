package com.ticoyk.forumapi.app.question;

import com.ticoyk.forumapi.app.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


public interface QuestionService {

    Question saveQuestion(Question question, Authentication authentication);
    PageDTO<Question> findAll(Pageable pageable);
    Question findQuestionById(Long id);

}
