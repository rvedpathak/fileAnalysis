package com.demo.task.dto;

import io.swagger.annotations.ApiParam;

import java.io.Serializable;

public class AnalysisRequest implements Serializable {
    @ApiParam(required = true,format = "UUID",allowEmptyValue = false, example = "3b38f38e-b727-4c56-942d-8cb0a742790c")
    private String fileID;

    public AnalysisRequest(String fileID) {
        this.fileID = fileID;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public AnalysisRequest() {
    }
}
