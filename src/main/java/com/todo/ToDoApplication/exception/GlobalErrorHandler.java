package com.todo.ToDoApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidInputException.class)
    public Error handleInvalidInputException(InvalidInputException e){
        return new Error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoDataException.class)
    public Error handleNoDataException(NoDataException e) {
        return new Error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
