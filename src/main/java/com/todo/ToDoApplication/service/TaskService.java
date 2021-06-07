package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.model.Complete;
import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public Task addTask(Task task) throws InvalidInputException {
        checkForExceptions(task);

        task.getList().getTasks().add(task);
        return repository.save(task);
    }

    private void checkForExceptions(Task task) throws InvalidInputException {
        if (task.getId() != null) {
            throw new InvalidInputException("Don't try to mess in DB!");
        }
        if (task.getName() == null || task.getName().isBlank()) {
            throw new InvalidInputException("Task name field can't be empty");
        }
        if (task.getComplete() == Complete.YES || task.getComplete() == null) {
            throw new InvalidInputException("Complete field is invalid");
        }
        if (task.getDate().isAfter(LocalDateTime.now()) || task.getDate() == null) {
            throw new InvalidInputException("Date field is invalid");
        }
        if (task.getList() == null) {
            throw new InvalidInputException("List is invalid");
        }
        if (task.getList().getId() == null) {
            throw new InvalidInputException("Task has to belong to list");
        }
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public void deleteTask(Long id) throws NoDataException {
        final Optional<Task> byId = repository.findById(id);
        repository.delete(byId.orElseThrow(() -> new NoDataException("There is no saved task with this id: " + id)));
    }
}
