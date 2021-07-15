package com.todo.ToDoApplication.repository;

import com.todo.ToDoApplication.dto.Complete;
import com.todo.ToDoApplication.dto.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query(value = "DELETE FROM Task t WHERE t.complete = :complete")
    void deleteAllByCompleted(@Param("complete") Complete complete);
}
