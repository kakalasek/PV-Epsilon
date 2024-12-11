package com.FileConverter;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface ConvertCommand {
       Integer execute(String file1, String file2);
}
