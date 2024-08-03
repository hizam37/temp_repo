package com.example.TaskUserService.repository;

import com.example.TaskUserService.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Task findByAssignedUserId(Long userId);
}
