package com.Exceptions;

public class JsonHandlingException extends Exception{
    public JsonHandlingException(String message, Throwable err) {
        super(message, err);
    }
}
