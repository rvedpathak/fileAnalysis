package com.demo.task.facade;

import com.demo.task.converter.TaskConverter;
import com.demo.task.dto.DFSResult;
import com.demo.task.dto.ReferenceFiles;
import com.demo.task.dto.TaskResult;
import com.demo.task.model.TaskResultEntity;
import com.demo.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TaskFacadeImpl implements TaskFacade{
    @Autowired
    private TaskService taskService;
    @Autowired
    TaskConverter taskConverter;
    @Override
    public TaskResult createTaskAnalysisTask(String fileID) {
        TaskResultEntity entity= taskService.createTaskAnalysisTask(fileID);
        TaskResult dto = taskConverter.toDto(entity);
        return dto;
    }

    @Override
    public TaskResult getTaskStatus(String taskId) {
        TaskResultEntity entity = taskService.getTaskStatus(taskId);
        TaskResult dto = taskConverter.toDto(entity);
        return dto;
    }

    @Override
    public ReferenceFiles references(String uuid) {
        Set<String> fileIds = taskService.references(uuid);
        ReferenceFiles files = new ReferenceFiles();
        files.setFileIDs(fileIds);
        files.setSize(fileIds.size());
        files.setUuid(uuid);
        return files;
    }

    @Override
    public DFSResult hasCycle(String fileID) {
        DFSResult result = new DFSResult();
        result.setHasCycle(taskService.hasCycle(fileID, new HashSet<String>()));
        return result;
    }
}
