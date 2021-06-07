package com.todo.ToDoApplication.exception;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Error {

    private LocalDateTime dateTime = LocalDateTime.now();
    private final Integer status;
    private String message = "Something bad has happened";
    private final String path;

    public Error(Integer status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
