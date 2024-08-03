package com.example.TaskUserService.service;

import com.example.TaskUserService.model.Task;
import com.example.TaskUserService.model.TaskStatus;
import com.example.TaskUserService.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;


    @Secured("ROLE_USER")
    public Task createTask(Task task)
    {
        return taskRepository.save(task);
    }


    @Secured("ROLE_USER")
    public List<Task> viewTasks()
    {
        List<Task> taskList = taskRepository.findAll();
        return Collections.synchronizedList(taskList);
    }


    @Secured("ROLE_USER")
    public Task updateTask(Task task,Long taskId)
    {
        Task existingTask = getTaskById(taskId);

        if(Objects.nonNull(existingTask))
        {
            existingTask.setTaskPriority(task.getTaskPriority());

            existingTask.setTitle(task.getTitle());

            existingTask.setAuthor(task.getAuthor());

            existingTask.setTaskStatus(task.getTaskStatus());

            existingTask.setComment(task.getComment());

            existingTask.setDescription(task.getDescription());

        }
        return taskRepository.save(existingTask);
    }

    public Task getTaskById(Long taskId)
    {
        return taskRepository.findById(taskId).orElseThrow(null);
    }

    @Secured("ROLE_USER")
    public void deleteTask(Task task)
    {
        taskRepository.delete(task);
    }


    @Secured("ROLE_USER")
    public Task assignedToUser(Long userId, Long taskId)
    {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setTaskStatus(task.getTaskStatus());
        return taskRepository.save(task);
     }


    @Secured("ROLE_PERFOMER")
     public Task updateStatus(TaskStatus taskStatus,Long userId)
     {
         Task task = taskRepository.findByAssignedUserId(userId);
         task.setTaskStatus(TaskStatus.valueOf(taskStatus.name()));
         return taskRepository.save(task);
     }

}
