package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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

    @PostMapping("/api/list")
    public ResponseEntity<TaskList> addList(@RequestBody @Valid TaskList list) throws InvalidInputException {
        final TaskList addedList = service.saveList(list);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addedList);
    }
}
