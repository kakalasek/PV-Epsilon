package com.Exceptions;

public class CsvWriteException extends Exception{
    public CsvWriteException(String message, Throwable err){
        super(message, err);
    }
}
