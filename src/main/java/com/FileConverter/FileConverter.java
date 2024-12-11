package com.FileConverter;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileConverter {

    private final ArrayList<CompletableFuture<Integer>> initiatedFileConversions = new ArrayList<>();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public ArrayList<CompletableFuture<Integer>> getInitiatedFileConversions(){
       return initiatedFileConversions;
    }

    public void executeCommand(ConvertCommand command, String file1, String file2){
        initiatedFileConversions.add(CompletableFuture.supplyAsync(() -> command.execute(file1, file2), executorService));
    }
}
