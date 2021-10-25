package com.demo.task.exceptions;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Entity Not found")
public class EntityNotFoundException extends RuntimeException {
    String id;
    public EntityNotFoundException(String id){
        super("Entity Not found :"+ id);
        this.id = id;
    }
}
