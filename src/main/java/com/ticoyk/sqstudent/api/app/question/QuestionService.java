package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.CommentDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


public interface QuestionService {

    Question saveQuestion(QuestionDTO question, Authentication authentication);
    Question updateQuestion(Long id, QuestionDTO question, Authentication authentication);
    Question removeQuestion(Long id, Authentication authentication);
    PageDTO<Question> findAll(Pageable pageable);
    Question findQuestionById(Long id);
    Comment addComment(Long questionId, CommentDTO commentDTO, Authentication authentication);
    Comment removeComment(Long commentId, Authentication authentication);
    PageDTO<Comment> findAllQuestionComments(Long questionId, Pageable pageable);
    Comment updateComment(Long id, CommentDTO commentDTO, Authentication authentication);

}
