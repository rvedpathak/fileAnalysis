package com.demo.task.utils;

import com.demo.task.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity handleNotFound(){
        return new ResponseEntity<>("Entity Not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleRuntimeError(){
        return new ResponseEntity("Runtime Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
