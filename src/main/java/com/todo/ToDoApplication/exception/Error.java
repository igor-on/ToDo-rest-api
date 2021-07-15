package com.todo.ToDoApplication.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class Error {

    private LocalDateTime timeStamp = LocalDateTime.now();
    private Integer status;
    private String error = "Something bad has happened";

    public Error(HttpStatus status, String error) {
        this.status = status.value();
        this.error = error;
    }
}
