package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.Question;
import com.ticoyk.sqstudent.api.app.question.QuestionService;
import com.ticoyk.sqstudent.api.auth.config.util.AuthUtil;
import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.auth.user.attributes.Authority;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private AuthUtil authUtil;
    private UserService userService;
    private QuestionService questionService;
    private CommentRepository commentRepository;
    private CommentConverter commentConverter;
    private CommentMSG commentMSG;

    @Override
    public Comment findCommentById(Long id) {
        Optional<Comment> optionalComment = this.commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        }
        throw new ContentNotFoundException(this.commentMSG.createCommentNotFoundException(id));
    }

    @Override
    public CommentDTO findCommentDTOById(Long id) {
        return this.commentConverter.convertCommentToDTO(this.findCommentById(id));
    }

    @Override
    public CommentDTO addComment(Long questionId, CommentFormDTO commentDTO, Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        Question question = this.questionService.findQuestionById(questionId);
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setQuestion(question);
        comment.setUser(user);
        return this.commentConverter.convertCommentToDTO(this.commentRepository.save(comment));
    }

    @Override
    public CommentDTO removeComment(Long commentId, Authentication authentication) {
        Optional<Comment> optionalComment = this.commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new ContentNotFoundException("Comment with id " + commentId+ " not Found!");
        }
        Comment comment = optionalComment.get();
        this.validateIfCanChangeComment(authentication, comment);

        this.commentRepository.delete(comment);
        return this.commentConverter.convertCommentToDTO(comment);
    }

    @Override
    public PageDTO<CommentDTO, Comment> findAllQuestionComments(Long questionId, Pageable pageable) {
        return this.commentConverter.convertToPageCommentDTO(this.commentRepository.findAllByQuestionId(questionId, pageable));
    }

    @Override
    public CommentDTO updateComment(Long id, CommentFormDTO commentDTO, Authentication authentication) {
        Comment comment = this.findCommentById(id);
        comment.setComment(commentDTO.getComment());
        this.validateIfCanChangeComment(authentication, comment);
        return this.commentConverter.convertCommentToDTO(comment);
    }

    private void validateIfCanChangeComment(Authentication authentication, Comment comment) {
        User user = this.userService.getUser(authentication.getName());
        if (comment.getUser().equals(user)) {
            return;
        }
        this.authUtil.isUserAllowed(List.of(Authority.APPUSER), user);
    }

}
