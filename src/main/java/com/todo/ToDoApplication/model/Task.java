package com.todo.ToDoApplication.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Entity
@Table(name = "things")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null
    private Long id;
    @NotNull
    @NotBlank
    @Length(max = 40)
    private String name;
    @Enumerated(EnumType.STRING)
    private Complete complete = Complete.NO;
    private LocalDateTime date = LocalDateTime.now();
    @NotNull
    @ManyToOne
    private TaskList list;
}
