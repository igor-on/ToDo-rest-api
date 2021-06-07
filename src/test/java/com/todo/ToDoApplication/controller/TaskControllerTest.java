package com.todo.ToDoApplication.controller;

import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.model.Complete;
import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskDTO;
import com.todo.ToDoApplication.model.TaskList;
import com.todo.ToDoApplication.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    public static final String NAME = "Do the cleaning up";
    public static final LocalDateTime NOW = LocalDateTime.of(2021, 6, 1, 14, 30);
    public static final TaskList LIST = new TaskList();
    public static final Task TASK_AFTER_SAVE_IN_DB = Task.builder()
            .id(1L)
            .name(NAME)
            .complete(Complete.NO)
            .date(NOW)
            .list(LIST)
            .build();
    @Mock
    private TaskService service;
    private TaskController controller;

    @BeforeEach
    void setUp() {
        LIST.setId(2L);
        controller = new TaskController(service);
    }

    @Test
    public void thatAddTaskWorksCorrectly() throws InvalidInputException {
        final Task validTask = new Task(null, NAME, Complete.NO, NOW, LIST);
        when(service.addTask(any())).thenReturn(TASK_AFTER_SAVE_IN_DB);

        final ResponseEntity<TaskDTO> actual = controller.addTask(validTask);
        final TaskDTO respJson = actual.getBody();

        assertThat(respJson).hasNoNullFieldsOrProperties();
        assertThat(respJson.getId()).isEqualTo(1);
        assertThat(respJson.getName()).isEqualTo(NAME);
        assertThat(respJson.getComplete()).isEqualTo("NO");
        assertThat(respJson.getDate()).isEqualTo("2021/06/01, 2:30 PM");
        assertThat(respJson.getListId()).isEqualTo(2);
    }

    @Test
    public void thatShowTasksWorksCorrectly() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        when(service.findAll()).thenReturn(tasks);

        final ResponseEntity<List<TaskDTO>> actual = controller.showTasks();
        final List<TaskDTO> respJson = actual.getBody();

        assertThat(actual.getStatusCode().value()).isEqualTo(200);
        assertThat(respJson.size()).isEqualTo(2);
        assertThat(respJson.get(0).getDate()).isEqualTo("2021/06/01, 2:30 PM");
        assertThat(respJson.get(1).getDate()).isEqualTo("2021/06/01, 2:30 PM");
        assertThat(respJson.get(0).getListId()).isEqualTo(2);
        assertThat(respJson.get(1).getListId()).isEqualTo(2);
    }

    @Test
    void thatRemoveTaskWorksCorrectly() throws NoDataException {
        final ResponseEntity actual = controller.removeTask(2L);

        assertThat(actual.getStatusCode().value()).isEqualTo(204);
    }
}