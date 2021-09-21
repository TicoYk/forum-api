package com.ticoyk.sqstudent.api.app.question;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class QuestionDTO {

    @Valid
    @NotBlank(message = "Title is required")
    private String title;

    @Column(columnDefinition="TEXT")
    @Size(max=999, message = "Text size should be between 0 and 999")
    private String description;

    public Question toQuestion() {
        Question question = new Question();
        question.setTitle(this.title);
        question.setDescription(this.description);
        return question;
    }

}
