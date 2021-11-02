package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.category.Category;
import com.ticoyk.sqstudent.api.app.category.CategoryService;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
import com.ticoyk.sqstudent.api.auth.config.util.AuthUtil;
import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.auth.user.attributes.Authority;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private UserService userService;
    private CategoryService categoryService;
    private QuestionRepository questionRepository;
    private QuestionMSG questionMSG;
    private QuestionConverter questionConverter;
    private AuthUtil authUtil;

    @Override
    public Question findQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = this.questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        }
        throw new ContentNotFoundException(this.questionMSG.createQuestionNotFoundException(questionId));
    }

    @Transactional
    public QuestionDTO findQuestionDTOById(Long questionId) {
        return this.questionConverter.convertQuestionToDTO(this.findQuestionById(questionId));
    }

    @Override
    public PageDTO<QuestionDTO, Question> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.questionConverter.convertToPageQuestionDTO(this.questionRepository.findAll(pageable));
    }

    @Override
    public PageDTO<QuestionDTO, Question> findAllByUserId(int page, int size, Long userId) {
        User user = this.userService.findUserById(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.questionConverter.convertToPageQuestionDTO(this.questionRepository.findAllByUser(pageable, user));
    }

    @Override
    public PageDTO<QuestionDTO, Question> findAllByCategory(int page, int size, Long categoryId) {
        Category category = this.categoryService.findCategoryById(categoryId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Question> questionsPage = this.questionRepository.findAllByCategory(pageable, category);
        return this.questionConverter.convertToPageQuestionDTO(questionsPage);
    }

    @Override
    public QuestionDTO saveQuestion(QuestionFormDTO questionFormDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = new Question();
        question.setTitle(questionFormDTO.getTitle());
        question.setDescription(questionFormDTO.getDescription());
        question.setIsChallenge(questionFormDTO.getIsChallenge());
        if(questionFormDTO.getCategoryId() != null) {
            question.setCategory(this.categoryService.findCategoryById(questionFormDTO.getCategoryId()));
        }
        question.setUser(user);
        return this.questionConverter.convertQuestionToDTO(this.questionRepository.save(question));
    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionFormDTO questionFormDTO, Authentication authentication) {
        Question question = this.findQuestionById(id);
        this.validateIfCanChangeQuestion(authentication, question);
        question.setTitle(questionFormDTO.getTitle());
        question.setDescription(questionFormDTO.getDescription());
        if(questionFormDTO.getCategoryId() != null) {
            question.setCategory(this.categoryService.findCategoryById(questionFormDTO.getCategoryId()));
        } else {
            question.setCategory(null);
        }
        return this.questionConverter.convertQuestionToDTO(this.questionRepository.save(question));
    }

    @Override
    public QuestionDTO removeQuestion(Long questionId, Authentication authentication) {
        Question question = this.findQuestionById(questionId);
        this.validateIfCanChangeQuestion(authentication, question);
        this.questionRepository.delete(question);
        return this.questionConverter.convertQuestionToDTO(question);
    }

    private void validateIfCanChangeQuestion(Authentication authentication, Question question) {
        User user = this.userService.getUser(authentication.getName());
        if (question.getUser().equals(user)) {
            return;
        }
        this.authUtil.isUserAllowed(List.of(Authority.APPUSER), user);
    }

}
