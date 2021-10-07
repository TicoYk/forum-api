package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.author.AuthorConverter;
import com.ticoyk.sqstudent.api.app.category.CategoryConverter;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class QuestionConverter {

    private CategoryConverter categoryConverter;
    private AuthorConverter authorConverter;

    public PageDTO<QuestionDTO, Question> convertToPageQuestionDTO(Page<Question> questions) {
        PageDTO<QuestionDTO, Question> pageDTO = new PageDTO<>(questions);
        List<QuestionDTO> questionsDTO =  questions.stream().map(this::convertQuestionToDTO).collect(Collectors.toList());
        pageDTO.setContent(questionsDTO);
        return pageDTO;
    }

    public QuestionDTO convertQuestionToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setAuthor(this.authorConverter.convertUserToAuthorDTO(question.getUser()));
        questionDTO.setTitle(question.getTitle());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setCategory(this.categoryConverter.convertToCategoryDTO(question.getCategory()));
        return questionDTO;
    }

}
