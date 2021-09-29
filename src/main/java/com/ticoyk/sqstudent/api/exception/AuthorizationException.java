package com.ticoyk.sqstudent.api.exception;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }

}
