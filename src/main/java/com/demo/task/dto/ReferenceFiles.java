package com.demo.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@ApiModel(description = "List of file IDs containing the UUID")
public class ReferenceFiles implements Serializable {
    @ApiParam(name = "fileIDs", format = "uuid")
    Set<String> fileIDs;
    @ApiParam(name = "Number of files in which UUID referenced", defaultValue = "0")
    int size;

    public Set<String> getFileIDs() {
        return fileIDs;
    }

    public void setFileIDs(Set<String> fileIDs) {
        this.fileIDs = fileIDs;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ReferenceFiles() {
    }

    @ApiParam(name = "uuid", format = "UUID")
    private String uuid;
}
