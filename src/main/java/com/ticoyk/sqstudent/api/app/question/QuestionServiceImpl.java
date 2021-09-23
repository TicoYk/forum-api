package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.category.Category;
import com.ticoyk.sqstudent.api.app.category.CategoryService;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private UserService userService;
    private CategoryService categoryService;
    private QuestionRepository questionRepository;
    private QuestionUtil questionUtil;

    @Override
    public Question saveQuestion(QuestionDTO questionDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = new Question();
        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        if(questionDTO.getCategoryId() != null) {
            question.setCategory(this.categoryService.findCategoryById(questionDTO.getCategoryId()));
        }
        question.setUser(user);
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long id, QuestionDTO questionDTO) {
        Question question = this.findQuestionById(id);
        question.setTitle(questionDTO.getTitle());
        question.setDescription(questionDTO.getDescription());
        if(questionDTO.getCategoryId() != null) {
            question.setCategory(this.categoryService.findCategoryById(questionDTO.getCategoryId()));
        } else {
            question.setCategory(null);
        }
        return questionRepository.save(question);
    }

    @Override
    public PageDTO<Question> findAll(Pageable pageable) {
        return new PageDTO<>(questionRepository.findAll(pageable));
    }

    @Transactional
    public Question findQuestionById(Long id) {
        Optional<Question> optionalQuestion = this.questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        }
        throw new ContentNotFoundException(questionUtil.createContentNotFoundException(id));
    }

}
