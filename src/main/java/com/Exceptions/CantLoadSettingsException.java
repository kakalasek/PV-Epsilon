package com.Exceptions;

public class CantLoadSettingsException extends Exception {
    public CantLoadSettingsException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
