package com.demo.task.events;

import com.demo.task.repository.TaskRepository;
import com.demo.task.utils.TaskConstants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class Worker implements Runnable{
    private final Object taskAvailableCondition;
    JedisPool jedisPool;
    Map<OperationType, OperationWorker> operationWorkerMap;
    TaskRepository repository;
    Semaphore semaphore;
    public Worker(Semaphore semaphore, JedisPool jedisPool, TaskRepository repository, Map<OperationType, OperationWorker> operationWorkerMap, Object pendingTasksCondition){
        this.jedisPool = jedisPool;
        this.operationWorkerMap = operationWorkerMap;
        this.repository = repository;
        this.taskAvailableCondition = pendingTasksCondition;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int retry = 0;
        while(true){
            try(Jedis jedis = jedisPool.getResource()) {
                List<String> values = jedis.blpop(900,TaskConstants.PENDING_TASKS_ID_KEY);
                if(values!=null && !values.isEmpty()) {
                    retry =0;
                    System.out.println("Tasks to be processed :"+ values);
                    //System.out.println("Operation found :"+ Thread.currentThread().getName());
                    for(int i=1;i<values.size();i++) {
                        String taskID = values.get(i);
                        if(taskID!=null && !taskID.trim().isEmpty()) {
                            String taskKey = TaskConstants.TASK_KEY_PREFIX + taskID;
                            Operation operation = new Operation(taskID, repository.getValueForField(taskKey, TaskConstants.FILE_ID), OperationType.UUID_EXTRACTION);
                            OperationWorker worker = operationWorkerMap.get(operation.operationType);
                            worker.handleOperation(operation);
                            System.out.println(Thread.currentThread().getName() + ", Handled Task :" + taskID);
                            semaphore.release();
                        }
                    }
                } else{
                    retry++;
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
