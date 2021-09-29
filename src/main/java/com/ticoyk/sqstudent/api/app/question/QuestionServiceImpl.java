package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.category.CategoryService;
import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.CommentRepository;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.auth.config.util.AuthUtil;
import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.auth.user.attributes.Authority;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private CommentRepository commentRepository;
    private QuestionUtil questionUtil;
    private AuthUtil authUtil;

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
    public Question updateQuestion(Long id, QuestionDTO questionDTO, Authentication authentication) {
        Question question = this.findQuestionById(id);
        this.validateIfCanChangeQuestion(authentication, question);
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
    public Question removeQuestion(Long commentId, Authentication authentication) {
        Question question = this.findQuestionById(commentId);
        this.validateIfCanChangeQuestion(authentication, question);
        this.questionRepository.delete(question);
        return question;
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
        throw new ContentNotFoundException(questionUtil.createQuestionNotFoundException(id));
    }

    @Override
    public Comment addComment(Long questionId, CommentDTO commentDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = this.findQuestionById(questionId);
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setQuestion(question);
        comment.setUser(user);
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment removeComment(Long commentId, Authentication authentication) {
        Optional<Comment> optionalComment = this.commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new ContentNotFoundException("Comment with id " + commentId+ " not Found!");
        }
        Comment comment = optionalComment.get();
        this.validateIfCanChangeComment(authentication, comment);

        this.commentRepository.delete(comment);
        return comment;
    }

    @Override
    public PageDTO<Comment> findAllQuestionComments(Long questionId, Pageable pageable) {
        return new PageDTO<>(commentRepository.findAllByQuestionId(questionId, pageable));
    }

    @Override
    public Comment updateComment(Long id, CommentDTO commentDTO, Authentication authentication) {
        Comment comment = this.findCommentById(id);
        comment.setComment(commentDTO.getComment());
        this.validateIfCanChangeComment(authentication, comment);
        return comment;
    }

    private Comment findCommentById(Long id) {
        Optional<Comment> optionalComment = this.commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        }
        throw new ContentNotFoundException(questionUtil.createCommentNotFoundException(id));
    }

    private void validateIfCanChangeQuestion(Authentication authentication, Question question) {
        User user = this.userService.getUser(authentication.getName());
        if (question.getUser().equals(user)) {
            return;
        }
        this.authUtil.isUserAllowed(List.of(Authority.APPUSER), user);
    }

    private void validateIfCanChangeComment(Authentication authentication, Comment comment) {
        User user = this.userService.getUser(authentication.getName());
        if (comment.getUser().equals(user)) {
            return;
        }
        this.authUtil.isUserAllowed(List.of(Authority.APPUSER), user);
    }

}
