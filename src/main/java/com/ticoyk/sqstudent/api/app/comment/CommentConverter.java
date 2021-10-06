package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.author.AuthorConverter;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentConverter {

    private AuthorConverter authorConverter;

    public PageDTO<CommentDTO, Comment> convertToPageCommentDTO(Page<Comment> comments) {
        PageDTO<CommentDTO, Comment> pageDTO = new PageDTO<>(comments);
        List<CommentDTO> commentsDTO =  comments.stream().map(this::convertCommentToDTO).collect(Collectors.toList());
        pageDTO.setContent(commentsDTO);
        return pageDTO;
    }

    public CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setComment(comment.getComment());
        commentDTO.setAuthor(this.authorConverter.convertUserToAuthorDTO(comment.getUser()));
        return commentDTO;
    }

}
