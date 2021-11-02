package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
import org.springframework.security.core.Authentication;


public interface QuestionService {

    Question findQuestionById(Long id);
    QuestionDTO findQuestionDTOById(Long id);
    PageDTO<QuestionDTO, Question> findAll(int page, int size);
    PageDTO<QuestionDTO, Question> findAllByUserId(int page, int size, Long userId);
    PageDTO<QuestionDTO, Question> findAllByCategory(int page, int size, Long categoryId);
    QuestionDTO saveQuestion(QuestionFormDTO question, Authentication authentication);
    QuestionDTO updateQuestion(Long id, QuestionFormDTO question, Authentication authentication);
    QuestionDTO removeQuestion(Long id, Authentication authentication);

}
