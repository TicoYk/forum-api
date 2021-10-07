package com.ticoyk.sqstudent.api.app.comment;

import org.springframework.stereotype.Component;

@Component
public class CommentMSG {
    public String createCommentNotFoundException(Long id) {
        return "Couldn't find Comment with the id: " + id;
    }

}
