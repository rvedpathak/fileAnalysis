package com.demo.task.utils;

public class TaskConstants {
    public static String TASK_KEY_PREFIX ="task:";
    public static String TASK_REFERENCE_FILE_KEY_PREFIX="task:list:";
    public static String FILE_IDS_KEY_PREFIX="files:list:";
    public static String GRAPH_KEY_PREFIX = "graph:";
    public static String TASK_CREATED_CHANNEL = "task_created";
    public static String FILE_ID = "fileID";
    public static String PENDING_TASKS_ID_KEY = "pending_tasks";
    public static String CREATION_TIME_STR = "creationTime";
    public static String COMPLETION_TIME_STR = "completionTime";
    public static String STATUS_STR = "status";
    public static String DATE_FORMAT = "EEE MMM d HH:mm:ss zzz yyyy";
    public static String FILE_PATH = "C:\\Users\\Rahul\\Downloads\\CSTest\\input";
    public static int THREAD_POOL_SIZE = 8;
    public static int CONCURRENT_TASKS_LIMIT = 10000;
    public static String UUID_REGEX = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b";
}
