package com.demo.task.controller;

import com.demo.task.converter.TaskConverter;
import com.demo.task.dto.AnalysisRequest;
import com.demo.task.dto.DFSResult;
import com.demo.task.dto.ReferenceFiles;
import com.demo.task.dto.TaskResult;
import com.demo.task.exceptions.EntityNotFoundException;
import com.demo.task.facade.TaskFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class TaskController {
    @Autowired
    TaskConverter converter;
    @Autowired
    TaskFacade taskFacade;
    // 1.B
    @ApiOperation(value = "Get Analysis Task Status", nickname = "getTaskStatus")
    @RequestMapping(path = "/analysis/{taskID}",produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseBody TaskResult getTaskStatus(@PathVariable("taskID") String taskID) {
        System.out.println("task Id :"+ taskID);
        TaskResult result = taskFacade.getTaskStatus(taskID);
        return result;
    }

    // 1.1 Create Analysis Task
    @ApiOperation(value = "Create Analysis Task", nickname = "createTask")
    @RequestMapping(path = "/analysis", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    @ResponseBody TaskResult createTask(@RequestBody AnalysisRequest request){
        System.out.println("file id :"+ request.getFileID());
        TaskResult result = taskFacade.createTaskAnalysisTask(request.getFileID());
        return result;
    }

    // 1.c
    @ApiOperation(value = "Get List of files in which UUID is referenced", nickname = "getReferenceFiles")
    @RequestMapping(path ="/files/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseBody ReferenceFiles getReferenceFiles(@PathVariable("uuid") String uuid){
        System.out.println("Find refs for :"+ uuid);
        return taskFacade.references(uuid);
    }

    // 5
    @ApiOperation(value = "Check if the chain of referenced files contains a loop/cycle",nickname = "hasCycle")
    @RequestMapping(path ="/files/cycle/{fileID}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @ResponseBody
    DFSResult hasCycle(@PathVariable("fileID") String fileId){
        DFSResult result = taskFacade.hasCycle(fileId);
        return result;
    }
}
