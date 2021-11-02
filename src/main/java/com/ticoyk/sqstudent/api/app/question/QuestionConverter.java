package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.author.AuthorConverter;
import com.ticoyk.sqstudent.api.app.category.CategoryConverter;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public QuestionDTO convertQuestionToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setAuthor(this.authorConverter.convertUserToAuthorDTO(question.getUser()));
        questionDTO.setTitle(question.getTitle());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setIsChallenge(question.getIsChallenge());
        questionDTO.setCommentsCount(0L);
        if (question.getComments() != null) {
            questionDTO.setCommentsCount((long) question.getComments().size());
        }
        questionDTO.setCategory(this.categoryConverter.convertToCategoryDTO(question.getCategory()));
        questionDTO.setCreatedAt(this.formatDate(question.getCreatedAt()));
        return questionDTO;
    }

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yy HH:mm");
        String date = localDateTime.format(dateFormat);
        return WordUtils.capitalize(date);
    }

}
