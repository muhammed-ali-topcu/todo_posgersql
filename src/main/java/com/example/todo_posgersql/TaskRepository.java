package com.example.todo_posgersql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE %:keyword% OR LOWER(t.description) LIKE %:keyword%")
    List<Task> search(@Param("keyword") String keyword);

}