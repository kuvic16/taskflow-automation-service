package com.thousand31.taskflow.dto.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank
    private String title;

    private String description;

    private LocalDateTime dueDate;

    private LocalDateTime reminderDate;
}
