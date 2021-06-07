package com.todo.ToDoApplication.mapper;

import com.todo.ToDoApplication.model.Task;
import com.todo.ToDoApplication.model.TaskDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskMapper {
    public static TaskDTO mapToTaskDTO(Task task) {

        final LocalDateTime date = task.getDate();
        String stringDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd, h:mm a"));

        return TaskDTO.builder()
                .id(task.getId().toString())
                .name(task.getName())
                .complete(task.getComplete().toString())
                .date(stringDate)
                .list_id(task.getList().getId().toString())
                .build();
    }
}
