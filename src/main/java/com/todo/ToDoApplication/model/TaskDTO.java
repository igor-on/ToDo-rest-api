package com.todo.ToDoApplication.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TaskDTO {

    private String id;
    private String name;
    private String complete;
    private String date;
    private String list_id;
}
