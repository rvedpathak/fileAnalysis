package com.demo.task.events;

public class Operation {
    String taskID;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Operation(String taskID, String fileID, OperationType operationType) {
        this.taskID = taskID;
        this.fileID = fileID;
        this.operationType = operationType;
    }

    String fileID;
    OperationType operationType;
}
