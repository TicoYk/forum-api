package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Valid
public class QuestionServiceImpl implements QuestionService {

    private UserService userService;
    private QuestionRepository questionRepository;
    private QuestionUtil questionUtil;

    @Override
    public Question saveQuestion(QuestionDTO questionDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = questionDTO.toQuestion();
        question.setUser(user);
        return questionRepository.save(question);
    }

    @Override
    public PageDTO<Question> findAll(Pageable pageable) {
        return new PageDTO<>(questionRepository.findAll(pageable));
    }

    public Question findQuestionById(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
           return question.get();
        }
        throw new ContentNotFoundException(questionUtil.createContentNotFoundException(id));
    }

}
