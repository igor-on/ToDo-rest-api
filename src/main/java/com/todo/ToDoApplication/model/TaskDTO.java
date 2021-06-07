package com.todo.ToDoApplication.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TaskDTO {

    private Long id;
    private String name;
    private String complete;
    private String date;
    private Long listId;
}
