package com.demo.task.converter;

import com.demo.task.dto.TaskResult;
import com.demo.task.dto.UUIDExtractionOperationResult;
import com.demo.task.model.TaskResultEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskConverter {
    public TaskResult toDto(TaskResultEntity entity){
        try {
            TaskResult result = new TaskResult();
            result.setFileID(entity.getFileID());
            result.setTaskID(entity.getTaskID());
            result.setTaskCompletionDate(entity.getCompletionTime());
            result.setTaskCreationDate(entity.getCreateTime());
            result.setStatus(entity.getTaskStatus());
            UUIDExtractionOperationResult uuidExtractionOperationResult = new UUIDExtractionOperationResult();
            if (!entity.getOperationEntityList().isEmpty())
                uuidExtractionOperationResult.setReferencedFiles(entity.getOperationEntityList().get(0).getResult());
            result.getTaskOperationResultList().add(uuidExtractionOperationResult);
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
