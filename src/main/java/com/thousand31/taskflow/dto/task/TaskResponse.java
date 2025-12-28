package com.thousand31.taskflow.dto.task;

import com.thousand31.taskflow.model.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime reminderTime;
    private TaskStatus status;
}
