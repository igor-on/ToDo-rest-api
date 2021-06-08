package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskListController {

    private final TaskListService service;

    @GetMapping("/api/list")
    public ResponseEntity<List<TaskList>> showLists(){
        final List<TaskList> all = service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(all);
    }
}
