package com.todo.ToDoApplication.service;

import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskList;
import com.todo.ToDoApplication.exception.InvalidInputException;
import com.todo.ToDoApplication.repository.TaskListRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskListServiceTest {

    private static final Set<Task> tasks = new HashSet<>();
    private static final TaskList TASK_LIST = new TaskList(1L, "Cleaning up", tasks);

    @Mock
    private TaskListRepository repository;
    @InjectMocks
    private TaskListService service;

    @AfterEach
    void setUp(){
        TASK_LIST.setId(1L);
    }

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

    @Test
    public void thatSaveListWorksCorrectly() throws InvalidInputException {
        when(repository.save(any())).thenReturn(TASK_LIST);

        final TaskList actual = service.saveList(new TaskList(null, "Cleaning up", tasks));

        System.out.println(actual);

        assertThat(actual).hasNoNullFieldsOrProperties();
        assertThat(actual.getTasks()).isEmpty();
        assertThat(actual.getName()).isEqualTo("Cleaning up");
    }

    @Test
    public void thatSaveListThrowsExceptionOnInvalidId() {

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.saveList(TASK_LIST));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("Don't try to mess in DB");
    }

    @Test
    public void thatSaveListThrowsExceptionOnNullName(){

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.saveList(new TaskList(null, null, tasks)));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("List name field is invalid");
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", ""})
    public void thatSaveListThrowsExceptionOnBlankName(String value){

        Throwable throwable = Assertions.assertThrows(InvalidInputException.class, () -> service.saveList(new TaskList(null, value, tasks)));

        assertThat(throwable)
                .isExactlyInstanceOf(InvalidInputException.class)
                .hasMessage("List name field is invalid");
    }
}