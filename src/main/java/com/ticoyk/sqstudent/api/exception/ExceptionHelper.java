package com.ticoyk.sqstudent.api.exception;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHelper extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException exception) {
        logger.error("Invalid Input Exception: " + exception.getMessage());
        return new ResponseEntity<>(new ExceptionRequestDTO(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ContentNotFoundException.class })
    public ResponseEntity<Object> handleContentNotFoundException(ContentNotFoundException exception) {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(new ExceptionRequestDTO(exception), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionRequestDTO(ex), HttpStatus.BAD_REQUEST);
    }

    @Data
    protected static class ExceptionRequestDTO {
        private String message;

        public ExceptionRequestDTO(Exception e) {
            this.message = e.getMessage();
        }
    }

}
