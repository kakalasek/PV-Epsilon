package com.FileConverter;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface ConvertCommand {
       int SUCCESS = 0;
       int IOERROR = -1;

       Integer execute(String file1, String file2);
}
