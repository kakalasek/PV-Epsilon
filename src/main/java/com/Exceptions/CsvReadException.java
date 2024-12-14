package com.Exceptions;

public class CsvReadException extends Exception{
    public CsvReadException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
