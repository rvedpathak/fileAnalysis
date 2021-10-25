package com.demo.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;

@ApiModel(description = "Result of each operation in a task")
public class TaskOperationResult implements Serializable {
    @ApiParam(name = "operation name", required = true)
    private String name;
    @ApiParam(name = "operation description")
    private String description;

    public TaskOperationResult() {
    }

    public TaskOperationResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskOperationResult(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
