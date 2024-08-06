package com.geektrust.backend.exception;

public class CourseCancelledException extends RuntimeException{
    public CourseCancelledException(){
        super();
    }
    public CourseCancelledException(String message){
        super(message);
    }
}
