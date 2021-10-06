package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.category.CategoryService;
import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.CommentConverter;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.comment.CommentRepository;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
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
    private QuestionMSG questionMSG;
    private QuestionConverter questionConverter;
    private CommentConverter commentConverter;
    private AuthUtil authUtil;

    public Question findQuestionById(Long questionId) {
        Optional<Question> optionalQuestion = this.questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        }
        throw new ContentNotFoundException(this.questionMSG.createQuestionNotFoundException(questionId));
    }

    @Override
    public QuestionDTO saveQuestion(QuestionFormDTO questionFormDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = new Question();
        question.setTitle(questionFormDTO.getTitle());
        question.setDescription(questionFormDTO.getDescription());
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

    @Override
    public PageDTO<QuestionDTO, Question> findAll(Pageable pageable) {
        return this.questionConverter.convertToPageQuestionDTO(questionRepository.findAll(pageable));
    }

    @Transactional
    public QuestionDTO findQuestionDTOById(Long questionId) {
        return this.questionConverter.convertQuestionToDTO(this.findQuestionById(questionId));
    }

    @Override
    public Comment addComment(Long questionId, CommentFormDTO commentDTO, Authentication authentication) {
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
    public PageDTO<CommentDTO, Comment> findAllQuestionComments(Long questionId, Pageable pageable) {
        return this.commentConverter.convertToPageCommentDTO(this.commentRepository.findAllByQuestionId(questionId, pageable));
    }

    @Override
    public Comment updateComment(Long id, CommentFormDTO commentDTO, Authentication authentication) {
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
        throw new ContentNotFoundException(this.questionMSG.createCommentNotFoundException(id));
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
