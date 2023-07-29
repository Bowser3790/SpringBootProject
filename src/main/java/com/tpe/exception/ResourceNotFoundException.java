package com.tpe.exception;



public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) { // to create this we RightClicked -> Generate-> Constructor for String
        super(message);
    }
}
