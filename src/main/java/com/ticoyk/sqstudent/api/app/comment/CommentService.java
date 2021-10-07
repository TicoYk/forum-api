package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CommentService {

    Comment findCommentById(Long id);
    CommentDTO findCommentDTOById(Long id);
    CommentDTO addComment(Long questionId, CommentFormDTO commentDTO, Authentication authentication);
    CommentDTO removeComment(Long commentId, Authentication authentication);
    PageDTO<CommentDTO, Comment> findAllQuestionComments(Long questionId, Pageable pageable);
    CommentDTO updateComment(Long id, CommentFormDTO commentDTO, Authentication authentication);

}
