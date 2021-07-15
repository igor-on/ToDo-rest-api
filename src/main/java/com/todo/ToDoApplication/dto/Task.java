package com.todo.ToDoApplication.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    private Long id;
    @NotBlank
    @Length(min = 3, max = 40)
    private String name;
    @Enumerated(EnumType.STRING)
    private Complete complete = Complete.NO;
    @PastOrPresent
    private LocalDateTime date = LocalDateTime.now();
    @NotNull
    @ManyToOne
    private TaskList list;
}
