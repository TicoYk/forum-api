package com.ticoyk.sqstudent.api.app.question.dto;

import com.ticoyk.sqstudent.api.app.author.dto.AuthorDTO;
import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long id;
    private String title;
    private String description;
    private AuthorDTO author;
    private CategoryDTO category;
    private String createdAt;

}
