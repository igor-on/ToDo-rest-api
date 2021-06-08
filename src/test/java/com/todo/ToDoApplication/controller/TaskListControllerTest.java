package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskList;
import com.todo.ToDoApplication.service.TaskListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskListControllerTest {

    private static final Set<Task> tasks = new HashSet<>();
    private static final TaskList TASK_LIST = new TaskList(1L, "Cleaning up", tasks);

    @Mock
    private TaskListService service;
    @InjectMocks
    private TaskListController controller;

    @Test
    public void thatShowListsWorksCorrectly(){
        List<TaskList> lists = new ArrayList<>();
        lists.add(TASK_LIST);
        lists.add(TASK_LIST);
        when(service.findAll()).thenReturn(lists);

        final ResponseEntity<List<TaskList>> actual = controller.showLists();

        assertThat(actual.getStatusCode().value()).isEqualTo(200);
        assertThat(actual.getBody()).hasSize(2);
        assertThat(actual.getBody().get(0)).hasNoNullFieldsOrProperties();
        assertThat(actual.getBody().get(0).getName()).isEqualTo("Cleaning up");
        assertThat(actual.getBody().get(1)).hasNoNullFieldsOrProperties();
    }
}