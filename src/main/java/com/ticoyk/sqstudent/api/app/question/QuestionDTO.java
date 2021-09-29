package com.ticoyk.sqstudent.api.app.question;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class QuestionDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max=999, message = "Text size should be between 0 and 999")
    private String description;

    @NotNull(message = "Category is required")
    private Long categoryId;

}
