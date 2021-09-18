package com.ticoyk.forumapi.app.question;

import com.ticoyk.forumapi.app.dto.PageDTO;
import com.ticoyk.forumapi.auth.user.User;
import com.ticoyk.forumapi.auth.user.UserService;
import com.ticoyk.forumapi.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private UserService userService;
    private QuestionRepository questionRepository;
    private QuestionUtil questionUtil;

    @Override
    public Question saveQuestion(Question question, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
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
