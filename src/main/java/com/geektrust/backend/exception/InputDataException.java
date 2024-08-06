package com.geektrust.backend.exception;

public class InputDataException extends RuntimeException{
    public InputDataException(){
        super();
    }
    public InputDataException(String message){
        super(message);
    }
}
