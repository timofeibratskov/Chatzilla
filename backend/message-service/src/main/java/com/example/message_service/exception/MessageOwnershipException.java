package com.example.message_service.exception;

public class MessageOwnershipException extends RuntimeException
{
    public MessageOwnershipException(String message){
        super(message);
    }
}
