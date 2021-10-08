package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.security.core.Authentication;

public interface CommentService {

    Comment findCommentById(Long commentId);
    CommentDTO findCommentDTOById(Long commentId);
    PageDTO<CommentDTO, Comment> findAllQuestionComments(int page, int size, Long questionId);
    CommentDTO addComment(Long questionId, CommentFormDTO commentDTO, Authentication authentication);
    CommentDTO removeComment(Long commentId, Authentication authentication);
    CommentDTO updateComment(Long commentId, CommentFormDTO commentDTO, Authentication authentication);

}
