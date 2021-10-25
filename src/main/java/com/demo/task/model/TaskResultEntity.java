package com.demo.task.model;

import com.demo.task.dto.TaskStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskResultEntity {
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    private String taskID;

    private TaskStatus taskStatus;
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public List<TaskOperationEntity> getOperationEntityList() {
        return operationEntityList;
    }

    public void setOperationEntityList(List<TaskOperationEntity> operationEntityList) {
        this.operationEntityList = operationEntityList;
    }

    private Date createTime;

    public TaskResultEntity() {
    }

    private Date completionTime;
    private String fileID;
    private List<TaskOperationEntity> operationEntityList = new ArrayList<>();
}
