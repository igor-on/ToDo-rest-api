package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.dto.Complete;
import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskListService listService;

    @Transactional
    public Task addTask(Task task) throws InvalidInputException, NoDataException {
        checkForExceptions(task);

        TaskList relatedList = listService.findList(task.getList().getId());
        relatedList.getTasks().add(task);
        task.setList(relatedList);

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
        final Task taskById = repository.findById(id).orElseThrow(() -> new NoDataException("There is no saved task with this id: " + id));

        TaskList relatedList = listService.findList(taskById.getList().getId());
        relatedList.getTasks().remove(taskById);

        repository.delete(taskById);
    }

    @Transactional
    public Task updateToComplete(Long id) throws NoDataException {
        Task byId =
                repository.findById(id).orElseThrow(() -> new NoDataException("There is no saved task with this id: " + id));

        byId.setComplete(Complete.YES);
        return byId;
    }

    @Transactional
    public void deleteAllCompleted() {
        repository.deleteAllByCompleted(Complete.YES);
    }
}
