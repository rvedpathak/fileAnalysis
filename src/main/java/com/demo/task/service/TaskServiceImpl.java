/**
 * Task Service to handle Task CRUD operations
 */
package com.demo.task.service;

import com.demo.task.events.EventPublisher;
import com.demo.task.model.TaskResultEntity;
import com.demo.task.repository.TaskRepository;
import com.demo.task.utils.TaskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepository repository;
    @Autowired
    EventPublisher eventPublisher;
    @Override
    public TaskResultEntity createTaskAnalysisTask(String fileID) {
        TaskResultEntity entity = repository.createTask(fileID);
        eventPublisher.publishEvent(entity);
        return entity;
    }

    @Override
    public TaskResultEntity getTaskStatus(String taskId) {
        return repository.getTaskResult(taskId);
    }

    @Override
    public Set<String> references(String uuid) {
        String key = TaskConstants.FILE_IDS_KEY_PREFIX+uuid;
        Set<String> fileIds = repository.getSetMembers(key);
        return fileIds;
    }

    @Override
    public boolean hasCycle(String fileID,Set<String> visited) {
        if(visited.contains(fileID)){
            return true;
        }
        visited.add(fileID);
        List<String> neighbors = repository.getListValues(TaskConstants.GRAPH_KEY_PREFIX+fileID);
        for(String neighbor : neighbors){
            visited.add(neighbor);
            if(hasCycle(neighbor, visited))
                return true;
        }
        return false;
    }
}
