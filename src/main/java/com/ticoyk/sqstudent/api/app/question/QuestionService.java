package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


public interface QuestionService {

    Question findQuestionById(Long id);
    QuestionDTO findQuestionDTOById(Long id);
    QuestionDTO saveQuestion(QuestionFormDTO question, Authentication authentication);
    QuestionDTO updateQuestion(Long id, QuestionFormDTO question, Authentication authentication);
    QuestionDTO removeQuestion(Long id, Authentication authentication);
    PageDTO<QuestionDTO, Question> findAll(Pageable pageable);
    Comment addComment(Long questionId, CommentFormDTO commentDTO, Authentication authentication);
    Comment removeComment(Long commentId, Authentication authentication);
    PageDTO<CommentDTO, Comment> findAllQuestionComments(Long questionId, Pageable pageable);
    Comment updateComment(Long id, CommentFormDTO commentDTO, Authentication authentication);

}
