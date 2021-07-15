package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.repository.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListService {

    private final TaskListRepository repository;

    public List<TaskList> findAll() {
        return repository.findAll();
    }

    public TaskList findList(long id) throws NoDataException {
        return repository.findById(id).orElseThrow(() -> new NoDataException("There is no saved list with this id " + id));
    }

    public TaskList saveList(TaskList list) throws InvalidInputException {
        checkForExceptions(list);

        return repository.save(list);
    }

    private void checkForExceptions(TaskList list) throws InvalidInputException {
        if(list.getId() != null){
            throw new InvalidInputException("Don't try to mess in DB");
        }
        if(list.getName() == null || list.getName().isBlank()){
            throw new InvalidInputException("List name field is invalid");
        }
    }

    @Transactional
    public void deleteList(Long id) throws NoDataException {
        final TaskList byId = repository.findById(id)
                .orElseThrow(() -> new NoDataException("There is no saved list with this id: " + id));

        repository.delete(byId);
    }
}
