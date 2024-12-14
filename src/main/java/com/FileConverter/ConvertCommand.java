package com.FileConverter;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface ConvertCommand {
       int SUCCESS = 0;
       int SETTINGS_ERROR = -11;
       int JSON_FORMATTING_ERROR = -72;
       int CSV_READ_ERROR = -33;
       int CSV_WRITE_ERROR = -34;
       int JSON_READ_ERROR = -73;
       int JSON_WRITE_ERROR = -74;

       String execute(String file1, String file2);
}
