package com.Exceptions;

public class JsonWriteException extends Exception{
    public JsonWriteException(String message, Throwable err) {
        super(message, err);
    }
}
