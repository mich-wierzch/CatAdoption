package com.CatShelter.CatShelter.exceptions;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException(String message){
        super(message);
    }
}
