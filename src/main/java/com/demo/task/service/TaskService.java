package com.demo.task.service;

import com.demo.task.model.TaskResultEntity;

import java.util.List;
import java.util.Set;

public interface TaskService {
    public TaskResultEntity createTaskAnalysisTask(String fileID);
    public TaskResultEntity getTaskStatus(String taskId);
    public Set<String> references(String uuid);
    public boolean hasCycle(String fileID, Set<String> visited);
}
