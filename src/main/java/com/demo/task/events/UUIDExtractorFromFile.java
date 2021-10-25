package com.demo.task.events;

import com.demo.task.dto.TaskStatus;
import com.demo.task.exceptions.FileNotFoundException;
import com.demo.task.repository.TaskRepository;
import com.demo.task.utils.TaskConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UUIDExtractorFromFile implements OperationWorker {
    private Pattern pattern;

    @Autowired
    TaskRepository repository;
    @PostConstruct
    public void init() {
        pattern = Pattern.compile(TaskConstants.UUID_REGEX);
    }

    public List<String> extractUUIDs(String fileId) {
        File file = new File(TaskConstants.FILE_PATH + "\\" + fileId);
        //System.out.println("File path :"+ file.getAbsolutePath());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.lines().collect(Collectors.toList());
            System.out.println("lines :"+ lines);
            List<String> result = lines.parallelStream().map(line -> {
                Matcher matcher = pattern.matcher(line);
                List<String> matches = new ArrayList<>();
                while(matcher.find()){
                    matches.add(matcher.group());
                }
                return matches;
            }).filter(list -> !list.isEmpty()).flatMap(List::stream).collect(Collectors.toList());
            return result;
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(fileId);
        }
    }

    @Override
    public void handleOperation(Operation operation) {
        System.out.println("Handle Operation of UUID Extraction : "+ operation.taskID);
        List<String> uuids = extractUUIDs(operation.fileID);
        System.out.println("Extracted UUIDs :"+ uuids);
        String key = TaskConstants.TASK_REFERENCE_FILE_KEY_PREFIX+operation.taskID;
        System.out.println("list key :"+ key);
        String graphKey = TaskConstants.GRAPH_KEY_PREFIX+ operation.fileID;
        if(!uuids.isEmpty()){
            repository.lpush(key, uuids.toArray(new String[0]));
            repository.lpush(graphKey, uuids.toArray(new String[0]));
        } else{
            repository.lpush(key, "");
            repository.lpush(graphKey, "");
        }
        for(String uuid :uuids) {
            String filesKey = TaskConstants.FILE_IDS_KEY_PREFIX + uuid;
            repository.sadd(filesKey,operation.fileID);
            System.out.println("Pushed :"+ filesKey+", file :" + operation.fileID);
        }
        String taskKey = TaskConstants.TASK_KEY_PREFIX+ operation.taskID;
        Map<String,String> values = new HashMap<>();
        values.put(TaskConstants.STATUS_STR, TaskStatus.COMPLETED.toString());
        values.put(TaskConstants.COMPLETION_TIME_STR, new Date().getTime()+"");
        repository.setValues(taskKey,values);
        System.out.println("Handle event complete");
    }
}
