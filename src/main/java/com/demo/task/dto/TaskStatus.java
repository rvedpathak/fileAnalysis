package com.demo.task.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Task Status")
public enum TaskStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    FAILED
}
