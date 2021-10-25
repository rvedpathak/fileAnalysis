package com.demo.task.repository;

import com.demo.task.dto.TaskResult;
import com.demo.task.model.TaskResultEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskRepository {
    public TaskResultEntity getTaskResult(String taskID);
    public TaskResultEntity createTask(String fileID);
    public List<String> getReferencedFiles(String fileId);
    public String getValueForField(String key, String field);
    public void lpush(String key, String... values);
    public void sadd(String key, String... values);
    public void setValueForField(String key, String field, String value);
    public void setValues(String key, Map<String,String> values);

    List<String> getListValues(String uuid);
    Set<String> getSetMembers(String key);
}
