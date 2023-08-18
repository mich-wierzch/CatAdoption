package com.CatShelter.CatShelter.exceptions;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String message){
        super(message);
    }
}
