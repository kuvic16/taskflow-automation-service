package com.thousand31.taskflow.service;

import com.thousand31.taskflow.dto.task.TaskRequest;
import com.thousand31.taskflow.dto.task.TaskResponse;
import com.thousand31.taskflow.exception.ResourceNotFoundException;
import com.thousand31.taskflow.model.Task;
import com.thousand31.taskflow.model.TaskStatus;
import com.thousand31.taskflow.model.User;
import com.thousand31.taskflow.repository.TaskRepository;
import com.thousand31.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    public TaskResponse createTask(TaskRequest request){
        User user = getCurrentUser();
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .reminderTime(request.getReminderDate())
                .status(TaskStatus.PENDING)
                .user(user)
                .build();
        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        User user = getCurrentUser();
        return taskRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request){
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setReminderTime(request.getReminderDate());

        return mapToResponse(taskRepository.save(task));
    }

    public void deleteTask(Long id){
        User user = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
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
