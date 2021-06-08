package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskList;
import com.todo.ToDoApplication.repository.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskListServiceTest {

    private static final Set<Task> tasks = new HashSet<>();
    private static final TaskList TASK_LIST = new TaskList(1L, "Cleaning up", tasks);

    @Mock
    private TaskListRepository repository;
    @InjectMocks
    private TaskListService service;

    @Test
    public void thatFindAllWorksCorrectly(){
        List<TaskList> lists = new ArrayList<>();
        lists.add(TASK_LIST);
        lists.add(TASK_LIST);
        when(repository.findAll()).thenReturn(lists);

        final List<TaskList> actual = service.findAll();

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getName()).isEqualTo("Cleaning up");
        assertThat(actual.get(0).getTasks()).isEmpty();
        assertThat(actual.get(1).getName()).isEqualTo("Cleaning up");
        assertThat(actual.get(1).getTasks()).isEmpty();
    }
}