package com.ticoyk.sqstudent.api.app.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentFormDTO {

    @NotBlank(message = "Comment is required")
    @Size(max=200, message = "Text size should be between 0 and 200")
    private String comment;

}
