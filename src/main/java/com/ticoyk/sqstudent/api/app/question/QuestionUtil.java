package com.ticoyk.sqstudent.api.app.question;

import org.springframework.stereotype.Component;

@Component
public class QuestionUtil {

    public String createQuestionNotFoundException(Long id) {
        return "Couldn't find Question with the id: " + id;
    }
    public String createCommentNotFoundException(Long id) {
        return "Couldn't find Comment with the id: " + id;
    }

}
