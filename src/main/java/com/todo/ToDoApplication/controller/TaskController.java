package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskDTO;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.mapper.TaskMapper;
import com.todo.ToDoApplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/tasks")
    public ResponseEntity<TaskDTO> addTask(@RequestBody @Valid Task task) throws InvalidInputException, NoDataException {
        final Task addedTask = service.addTask(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.mapToTaskDTO(addedTask));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/tasks")
    public ResponseEntity<List<TaskDTO>> showTasks() {
        List<Task> allTasks = service.findAll();
        List<TaskDTO> allMapped = allTasks.stream()
                .map(TaskMapper::mapToTaskDTO)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allMapped);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/api/tasks/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable("id") Long id) throws NoDataException {
        service.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/api/tasks/{id}")
    public ResponseEntity<TaskDTO> changeToComplete(@PathVariable("id") Long id) throws NoDataException {
        final Task updatedTask = service.updateToComplete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TaskMapper.mapToTaskDTO(updatedTask));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/api/tasks/remove")
    public ResponseEntity<Void> removeCompleted() {
        service.deleteAllCompleted();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
