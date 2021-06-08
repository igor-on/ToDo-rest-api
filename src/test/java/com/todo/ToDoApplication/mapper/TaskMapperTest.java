package com.todo.ToDoApplication.mapper;

import com.todo.ToDoApplication.dto.Complete;
import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskDTO;
import com.todo.ToDoApplication.dto.TaskList;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    @Test
    public void thatMapToTaskDTOWorksCorrectly(){
        final TaskList list = new TaskList();
        list.setId(2L);
        final Task taskToMap = new Task(1L, "Do the cleaning up", Complete.NO, LocalDateTime.of(2021, 6, 1, 14, 30), list);

        final TaskDTO actual = TaskMapper.mapToTaskDTO(taskToMap);

        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("Do the cleaning up");
        assertThat(actual.getComplete()).isEqualTo("NO");
        assertThat(actual.getDate()).isEqualTo("2021/06/01, 2:30 PM");
        assertThat(actual.getListId()).isEqualTo(2);
    }
}