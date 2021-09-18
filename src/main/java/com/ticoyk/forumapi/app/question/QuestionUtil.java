package com.ticoyk.forumapi.app.question;

import org.springframework.stereotype.Component;

@Component
public class QuestionUtil {

    public String createContentNotFoundException(Long id) {
        return "Couldn't find Question with the id: " + id;
    }
}
