package com.geektrust.backend.exception;

public class CourseFullException extends RuntimeException{
    public CourseFullException(){
        super();
    }
    public CourseFullException(String message){
        super(message);
    }
}
