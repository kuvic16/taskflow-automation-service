package com.thousand31.taskflow.repository;

import com.thousand31.taskflow.dto.task.TaskRequest;
import com.thousand31.taskflow.dto.task.TaskResponse;
import com.thousand31.taskflow.model.Task;
import com.thousand31.taskflow.model.TaskStatus;
import com.thousand31.taskflow.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    public TaskResponse createTask(TaskRequest request){
        User user = getCurrentUser();
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .reminderTime(request.getReminderDate())
                .status(TaskStatus.PENDING)
                .build();
        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    private TaskResponse mapToResponse(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .reminderTime(task.getReminderTime())
                .status(task.getStatus())
                .build();
    }
}
