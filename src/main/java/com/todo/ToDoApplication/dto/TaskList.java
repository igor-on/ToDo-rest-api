package com.todo.ToDoApplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "tasks")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "list")
    Set<Task> tasks = new HashSet<>();
}
