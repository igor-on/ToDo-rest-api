package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.dto.Complete;
import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.exception.NoDataException;
import com.todo.ToDoApplication.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    @Mock
    private TaskListService listService;
    private TaskService service;

    @BeforeEach
    void setUp() {
        LIST.setId(2L);
        service = new TaskService(repository, listService);
    }

    @Test
    public void thatAddTaskWorksCorrectly() throws InvalidInputException, NoDataException {
        final Task validTask = new Task(null, NAME, Complete.NO, NOW, LIST);
        when(repository.save(any())).thenReturn(TASK_AFTER_SAVE_IN_DB);
        Set<Task> tasks = new HashSet<>();
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        when(listService.findList(anyLong())).thenReturn(new TaskList(2L, "clean", tasks));
        final Task actual = service.addTask(validTask);

        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual).hasFieldOrPropertyWithValue("complete", Complete.NO);
        assertThat(actual.getList().getId()).isNotNull();
    }

    @Test
    public void thatAddTaskThrowsExceptionOnGivenId() {
        final Task invalidTask = new Task(3L, NAME, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Don't try to mess in DB!");
    }

    @Test
    public void thatAddTaskThrowsExceptionOnNullName() {
        final Task invalidTask = new Task(null, null, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task name field can't be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"    ", ""})
    public void thatAddTaskThrowsExceptionOnBlankName(String value) {
        final Task invalidTask = new Task(null, value, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task name field can't be empty");
    }

    @Test
    public void thatAddTaskThrowsExceptionOnInvalidCompleteField() {
        final Task invalidTask = new Task(null, NAME, Complete.YES, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Complete field is invalid");
    }

    @Test
    public void thatAddTaskThrowsExceptionOnInvalidDate() {
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW.plusSeconds(5), LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Date field is invalid");
    }

    @Test
    public void thatAddTaskThrowsExceptionOnInvalidList() {
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW, null);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("List is invalid");
    }

    @Test
    public void thatAddTaskThrowsExceptionOnInvalidListId() {
        LIST.setId(null);
        final Task invalidTask = new Task(null, NAME, Complete.NO, NOW, LIST);

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.addTask(invalidTask));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Task has to belong to list");
    }

    @Test
    public void thatFindAllWorksCorrectly() {
        final List<Task> tasks = new ArrayList<>();
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        when(repository.findAll()).thenReturn(tasks);

        final List<Task> all = service.findAll();

        assertThat(all)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void thatDeleteTaskWorksCorrectly() throws NoDataException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(TASK_AFTER_SAVE_IN_DB));
        Set<Task> tasks = new HashSet<>();
        tasks.add(TASK_AFTER_SAVE_IN_DB);
        when(listService.findList(anyLong())).thenReturn(new TaskList(2L, "clean", tasks));
        doNothing().when(repository).delete(any());

        service.deleteTask(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(any());
    }

    @Test
    public void thatDeleteTaskThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Throwable throwable = Assertions.assertThrows(NoDataException.class, () -> service.deleteTask(1L));

        assertThat(throwable).isExactlyInstanceOf(NoDataException.class)
        .hasMessage("There is no saved task with this id: " + 1);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).delete(any());
    }

    @Test
    public void thatUpdateToCompleteWorksCorrectly() throws NoDataException {
        when(repository.findById(1L)).thenReturn(Optional.of(TASK_AFTER_SAVE_IN_DB));

        final Task actual = service.updateToComplete(1L);

        assertThat(actual.getComplete()).isEqualTo(Complete.YES);
        assertThat(actual.getComplete()).isNotEqualTo(Complete.NO);
        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo(NAME);
        assertThat(actual.getDate()).isEqualTo(NOW);
        assertThat(actual.getList().getId()).isEqualTo(2);
    }

    @Test
    public void thatUpdateToCompleteThrowsException() {
        Throwable throwable = Assertions.assertThrows(NoDataException.class, () ->service.updateToComplete(1L));

        assertThat(throwable)
                .isExactlyInstanceOf(NoDataException.class)
                .hasMessage("There is no saved task with this id: " + 1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void thatDeleteAllCompletedWorksCorrectly() {
        doNothing().when(repository).deleteAllByCompleted(Complete.YES);

        service.deleteAllCompleted();

        verify(repository, times(1)).deleteAllByCompleted(Complete.YES);
        verifyNoInteractions(listService);
    }
}
