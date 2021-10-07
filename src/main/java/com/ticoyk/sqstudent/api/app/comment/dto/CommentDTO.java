package com.ticoyk.sqstudent.api.app.comment.dto;

import com.ticoyk.sqstudent.api.app.author.dto.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {

    private Long id;
    private String comment;
    private AuthorDTO author;
    private String createdAt;

}
