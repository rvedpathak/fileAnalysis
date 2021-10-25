package com.demo.task.events;

import com.demo.task.model.TaskResultEntity;
import com.demo.task.utils.TaskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisTaskEventPublisher implements EventPublisher {

    @Autowired
    JedisPool jedisPool;

    @Override
    public void publishEvent(TaskResultEntity entity) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(TaskConstants.TASK_CREATED_CHANNEL, entity.getTaskID());
            jedis.rpush(TaskConstants.PENDING_TASKS_ID_KEY, entity.getTaskID());
            //jedis.rpush()
            System.out.println("Published event for :" + entity.getTaskID());
        }
    }
}
