package com.demo.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
@ApiModel(description = "Describes whether the file has cycle or not")
public class DFSResult {
    @ApiParam(required = true, name = "fileID", example = "3b38f38e-b727-4c56-942d-8cb0a742790c", format = "uuid")
    private String fileID;
    @ApiParam(name = "hasCycle", defaultValue = "false", example = "false")
    boolean hasCycle;

    public boolean isHasCycle() {
        return hasCycle;
    }

    public void setHasCycle(boolean hasCycle) {
        this.hasCycle = hasCycle;
    }

    public DFSResult() {
    }
}
