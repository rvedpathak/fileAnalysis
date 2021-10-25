package com.demo.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "UUID extract operation details")
public class UUIDExtractionOperationResult extends TaskOperationResult{
    public UUIDExtractionOperationResult(){
        super("UUIDExtractionOperationResult","Extracts UUID from referenced files");
        referencedFiles = new ArrayList<>();
    }
    @ApiParam(name = "Referenced Files List")
    List<String> referencedFiles;

    public List<String> getReferencedFiles() {
        return referencedFiles;
    }

    public void setReferencedFiles(List<String> referencedFiles) {
        this.referencedFiles = referencedFiles;
    }
}
