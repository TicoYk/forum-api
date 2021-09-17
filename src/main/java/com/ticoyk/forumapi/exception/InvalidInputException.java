package com.ticoyk.forumapi.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }

}
