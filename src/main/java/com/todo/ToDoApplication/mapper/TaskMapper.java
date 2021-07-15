package com.todo.ToDoApplication.mapper;

import com.todo.ToDoApplication.dto.Task;
import com.todo.ToDoApplication.dto.TaskDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskMapper {
    public static TaskDTO mapToTaskDTO(Task task) {

        final LocalDateTime date = task.getDate();
        String stringDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd, h:mm a"));

        return TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .complete(task.getComplete().toString())
                .date(stringDate)
                .listId(task.getList().getId())
                .build();
    }
}
