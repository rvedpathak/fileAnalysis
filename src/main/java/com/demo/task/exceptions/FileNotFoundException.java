package com.demo.task.exceptions;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String file){
        super("File Not Found :"+ file);
    }
}
