package com.demo.task.repository;

import com.demo.task.dto.TaskStatus;
import com.demo.task.exceptions.EntityNotFoundException;
import com.demo.task.model.TaskOperationEntity;
import com.demo.task.model.TaskResultEntity;
import com.demo.task.utils.TaskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Repository
public class RedisTaskRepositoryImpl implements TaskRepository{
    @Autowired
    JedisPool jedisPool;

    @Override
    public TaskResultEntity getTaskResult(String taskID) {
        String key = TaskConstants.TASK_KEY_PREFIX+taskID;
        try(Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists(key)) {
                System.out.println("Entity Not found :" + taskID);
                throw new EntityNotFoundException(taskID);
            }
            List<String> values = jedis.hmget(key, TaskConstants.FILE_ID, TaskConstants.STATUS_STR,
                    TaskConstants.CREATION_TIME_STR, TaskConstants.COMPLETION_TIME_STR);

            TaskResultEntity result = new TaskResultEntity();
            result.setTaskID(taskID);
            result.setFileID(values.get(0));
            //System.out.println("values :"+ values);
            result.setTaskStatus(TaskStatus.valueOf(values.get(1)));

            String creationTime = values.get(2);
            //SimpleDateFormat sdf = new SimpleDateFormat(TaskConstants.DATE_FORMAT);
            Date creationDate = null;
            try {
                creationDate = new Date(Long.parseLong(values.get(2)));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to parse date :" + creationTime);
            }
            result.setCreateTime(creationDate);
            if (values.get(3) != null) {
                try {
                    result.setCompletionTime(new Date(Long.parseLong(values.get(3))));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unable to parse date :" + values.get(3));
                }
            }
            if (values.get(1).equalsIgnoreCase(TaskStatus.COMPLETED.toString())) {
                String kk = TaskConstants.TASK_REFERENCE_FILE_KEY_PREFIX + taskID;
                System.out.println("list key repo:" + kk);
                List<String> vvs = jedis.lrange(kk, 0, -1);
                vvs.remove(values.get(0));
                System.out.println("references :" + vvs);
                TaskOperationEntity operationEntity = new TaskOperationEntity();
                operationEntity.setResult(vvs);
                result.getOperationEntityList().add(operationEntity);
                //
            }
            //System.out.println("Repo Status :" + result);
            return result;
        }
    }

    @Override
    public TaskResultEntity createTask(String fileID) {
        // Generate Random ID
        UUID uuid = UUID.randomUUID();
        String key = TaskConstants.TASK_KEY_PREFIX+uuid.toString();
        Map<String, String> fields = new HashMap<>();
        fields.put(TaskConstants.FILE_ID, fileID);
        fields.put(TaskConstants.STATUS_STR, TaskStatus.IN_PROGRESS.toString());
        Date creationTime = new Date();
        fields.put(TaskConstants.CREATION_TIME_STR, creationTime.getTime()+"");
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.hmset(key, fields);
        }
        TaskResultEntity entity = new TaskResultEntity();
        entity.setTaskID(uuid.toString());
        entity.setCreateTime(creationTime);
        entity.setFileID(fileID);
        entity.setTaskStatus(TaskStatus.IN_PROGRESS);
        //System.out.println("Task Created repo");
        return entity;
    }

    @Override
    public List<String> getReferencedFiles(String fileId) {
        return null;
    }

    @Override
    public String getValueForField(String key, String field) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.hmget(key, field).get(0);
        }
    }

    @Override
    public void lpush(String key, String... value) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, value);
        }
        //jedis.close();
    }

    @Override
    public void sadd(String key, String... values) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(key, values);
        }
    }

    @Override
    public void setValueForField(String key, String field, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }

    @Override
    public void setValues(String key, Map<String, String> values) {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.hmset(key, values);
        }
    }

    @Override
    public List<String> getListValues(String key) {
        //String key = TaskConstants.FILE_IDS_KEY_PREFIX+uuid;
        //System.out.println("List Key find:"+ key);
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, 0, -1);
        }
    }

    @Override
    public Set<String> getSetMembers(String key) {
        try(Jedis jedis = jedisPool.getResource()){
            return jedis.smembers(key);
        }
    }
}
