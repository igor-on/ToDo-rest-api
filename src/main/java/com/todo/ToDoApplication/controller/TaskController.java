package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.mapper.TaskMapper;
import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskDTO;
import com.todo.ToDoApplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/api/task")
    public ResponseEntity<TaskDTO> addTask(@RequestBody Task task){
        final Task addedTask = service.addTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.mapToTaskDTO(addedTask));
    }

}
