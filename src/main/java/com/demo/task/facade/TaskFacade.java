package com.demo.task.facade;

import com.demo.task.dto.DFSResult;
import com.demo.task.dto.ReferenceFiles;
import com.demo.task.dto.TaskResult;

import java.util.List;

public interface TaskFacade {
    public TaskResult createTaskAnalysisTask(String fileID);
    public TaskResult getTaskStatus(String taskId);
    public ReferenceFiles references(String uuid);
    public DFSResult hasCycle(String fileID);
}
