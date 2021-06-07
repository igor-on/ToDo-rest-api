package com.todo.ToDoApplication.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "things")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "ENUM")
    @Enumerated(EnumType.STRING)
    private Complete complete = Complete.NO;
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne
    private TaskList list;

}
