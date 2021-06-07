package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.exception.Error;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.mapper.TaskMapper;
import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskDTO;
import com.todo.ToDoApplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/api/task")
    public ResponseEntity<TaskDTO> addTask(@RequestBody @Valid Task task) throws InvalidInputException {
        final Task addedTask = service.addTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.mapToTaskDTO(addedTask));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value =  InvalidInputException.class)
    public Error handleException(InvalidInputException e){
        return new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "/api/task");
    }
}
