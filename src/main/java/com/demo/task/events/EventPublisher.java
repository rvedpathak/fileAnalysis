package com.demo.task.events;

import com.demo.task.model.TaskResultEntity;

import java.io.Serializable;

public interface EventPublisher {
    public void publishEvent(TaskResultEntity entity);
}
