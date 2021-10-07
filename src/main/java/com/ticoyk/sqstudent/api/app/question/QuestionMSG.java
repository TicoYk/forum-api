package com.ticoyk.sqstudent.api.app.question;

import org.springframework.stereotype.Component;

@Component
public class QuestionMSG {

    public String createQuestionNotFoundException(Long id) {
            return "Couldn't find Question with the id: " + id;
    }

}
