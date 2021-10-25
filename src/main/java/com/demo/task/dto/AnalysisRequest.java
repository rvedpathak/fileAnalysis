package com.demo.task.dto;

import com.demo.task.utils.TaskConstants;
import io.swagger.annotations.ApiParam;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class AnalysisRequest implements Serializable {
    @ApiParam(required = true,format = "UUID",allowEmptyValue = false, example = "3b38f38e-b727-4c56-942d-8cb0a742790c")
    @NotNull
    @Pattern(regexp = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")
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
