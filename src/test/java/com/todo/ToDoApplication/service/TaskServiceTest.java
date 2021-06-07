package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.model.Complete;
import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskList;
import com.todo.ToDoApplication.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TaskServiceTest {

    private static final String NAME = "Do the cleaning up";
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final TaskList LIST = new TaskList();
    private static final Task TASK_AFTER_SAVE_IN_DB = Task.builder()
            .id(1L)
            .name("Do the cleaning up")
            .complete(Complete.NO)
            .date(NOW)
            .list(LIST)
            .build();

    @Mock
    private TaskRepository repository;
    private TaskService service;

    @BeforeEach
    void setUp() {
        LIST.setId(2L);
        service = new TaskService(repository);
    }

    @Test
    public void testThatAddTaskWorksCorrectly() throws InvalidInputException {
        final Task validTask = new Task(null, NAME, Complete.NO, NOW, LIST);
        when(repository.save(any())).thenReturn(TASK_AFTER_SAVE_IN_DB);

        final Task actual = service.addTask(validTask);

        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual).hasFieldOrPropertyWithValue("complete", Complete.NO);
        assertThat(actual.getList().getId()).isNotNull();
        assertThat(actual.getList().getName()).isNull();
    }

    @Test
    public void testThatAddTaskThrowsExceptionOnGivenId() {
        final Task invalidTask = new Task(3L, NAME, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Don't try to mess in DB!");
    }

    @Test
    public void testThatAddTaskThrowsExceptionOnNullName() {
        final Task invalidTask = new Task(null, null, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task name field can't be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"    ", ""})
    public void testThatAddTaskThrowsExceptionOnBlankName(String value) {
        final Task invalidTask = new Task(null, value, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task name field can't be empty");
    }

    @Test
    public void testThatAddTaskThrowsExceptionOnInvalidCompleteField() {
        final Task invalidTask = new Task(null, NAME, Complete.YES, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Complete field is invalid");
    }

    @Test
    public void testThatAddTaskThrowsExceptionOnInvalidDate() {
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW.plusSeconds(5), LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Date field is invalid");
    }

    @Test
    public void testThatAddTaskThrowsExceptionOnInvalidList() {
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW, null);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("List is invalid");
    }
    @Test
    public void testThatAddTaskThrowsExceptionOnInvalidListId() {
        LIST.setId(null);
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task has to belong to list");
    }

    @Test
    public void testThatFindAllWorksCorrectly(){
        final List<Task> tasks = new ArrayList<>();
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        when(repository.findAll()).thenReturn(tasks);

        final List<Task> all = service.findAll();

        assertThat(all)
                .isNotEmpty()
                .hasSize(2);
    }
}