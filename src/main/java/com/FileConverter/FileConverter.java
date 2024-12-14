package com.FileConverter;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class takes care of the pool of virtual threads for file conversions and is responsible for issuing the file conversion command
 * in a virtual thread, thus asynchronously.
 */
public class FileConverter {

    private final ArrayList<CompletableFuture<String>> initiatedFileConversions = new ArrayList<>();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public ArrayList<CompletableFuture<String>> getInitiatedFileConversions(){
       return initiatedFileConversions;
    }

    public void executeCommand(ConvertCommand command, String file1, String file2) {
        initiatedFileConversions.add(CompletableFuture.supplyAsync(() -> command.execute(file1, file2), executorService));
    }
}
