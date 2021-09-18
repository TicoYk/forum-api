package com.ticoyk.sqstudent.api.exception;


public class ContentNotFoundException extends RuntimeException {

    public ContentNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}

