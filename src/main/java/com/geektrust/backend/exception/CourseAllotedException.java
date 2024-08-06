package com.geektrust.backend.exception;

public class CourseAllotedException extends RuntimeException{
    public CourseAllotedException(){
        super();
    }
    public CourseAllotedException(String message){
        super(message);
    }
}
