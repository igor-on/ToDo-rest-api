package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public Task addTask(Task task){
        task.getList().getTasks().add(task);
        return repository.save(task);
    }
}
