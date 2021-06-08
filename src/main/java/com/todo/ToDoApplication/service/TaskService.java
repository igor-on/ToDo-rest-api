package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.dto.Complete;
import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void deleteTask(Long id) throws NoDataException {
        final Optional<Task> byId = repository.findById(id);
        repository.delete(byId.orElseThrow(() -> new NoDataException("There is no saved task with this id: " + id)));
    }

    @Transactional
    public Task updateToComplete(Long id) throws NoDataException {
        Task byId =
                repository.findById(id).orElseThrow(() -> new NoDataException("There is no saved task with this id: " + id));

        byId.setComplete(Complete.YES);
        return byId;
    }
}
