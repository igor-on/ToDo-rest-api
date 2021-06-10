package com.todo.ToDoApplication.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class Error {

    private LocalDateTime dateTime = LocalDateTime.now();
    private HttpStatus status;
    private String message = "Something bad has happened";

    public Error(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
