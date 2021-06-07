package com.todo.ToDoApplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lists")
@NoArgsConstructor
@Getter
@Setter
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "list")
    Set<Task> tasks = new HashSet<>();
}
