package com.demo.task.events;

import com.demo.task.repository.TaskRepository;
import com.demo.task.utils.TaskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Listens on Redis channel for tasks created, loads file and extract the UUID from them.
 */
@Component
public class EventListener {

    @Autowired
    TaskRepository repository;
    Map<OperationType, OperationWorker> workerMap = new HashMap<>();

    ExecutorService executor;

    JedisPubSub jedisPubSub;

    @Autowired
    JedisPool jedisPool;
    public Map<OperationType, OperationWorker> getWorkerMap() {
        return workerMap;
    }

    @Autowired
    UUIDExtractorFromFile uuidExtractorFromFile;
    Semaphore semaphore = new Semaphore(TaskConstants.CONCURRENT_TASKS_LIMIT);
    Object tasksAvaiable = new Object();
    @PostConstruct
    public void init(){
        workerMap.put(OperationType.UUID_EXTRACTION, uuidExtractorFromFile);
        jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                handleMessage(channel, message);
            }

            private void handleMessage(String channel, String message) {
                System.out.println("Received msg :"+ message+", on channel :"+ channel);
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Unable to get permit");
                }
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("Subscribed to channel :"+ channel);
                super.onSubscribe(channel, subscribedChannels);
                executor = Executors.newFixedThreadPool(TaskConstants.THREAD_POOL_SIZE);
                for(int i=0;i<TaskConstants.THREAD_POOL_SIZE;i++){
                    executor.execute(new Worker(semaphore, jedisPool, repository,workerMap, tasksAvaiable));
                }
                System.out.println("Created workers");
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                System.out.println("Unsubscribed channel :"+ channel);
                super.onUnsubscribe(channel, subscribedChannels);
            }
        };
        // Start Listener thread
        new Thread(this::startListsening).start();
        System.out.println("EventListener initialized");
    }

    public void startListsening(){
        Jedis jedis = jedisPool.getResource();
        jedis.subscribe(jedisPubSub, TaskConstants.TASK_CREATED_CHANNEL);
        System.out.println("Started listening :"+Thread.currentThread().getName());
    }
    private List<String> extractReferencedIDs(String fileId) {
        List<String> result = new ArrayList<>();
        return result;
    }

    /*@Override
    public void run() {
        System.out.println("Starting listening on events");
        new Thread(this::startListsening).start();
    }*/
}
