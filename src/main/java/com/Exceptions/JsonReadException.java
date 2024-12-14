package com.Exceptions;

public class JsonReadException extends Exception{
    public JsonReadException(String message, Throwable err) {
        super(message, err);
    }
}
