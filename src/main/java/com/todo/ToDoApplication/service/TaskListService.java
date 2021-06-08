package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.model.TaskList;
import com.todo.ToDoApplication.repository.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListService {

    private final TaskListRepository repository;

    public List<TaskList> findAll() {
        return repository.findAll();
    }
}
