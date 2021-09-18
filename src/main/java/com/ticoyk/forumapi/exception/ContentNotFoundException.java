package com.ticoyk.forumapi.exception;


public class ContentNotFoundException extends RuntimeException {

    public ContentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}

