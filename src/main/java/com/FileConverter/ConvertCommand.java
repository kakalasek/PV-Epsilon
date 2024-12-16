package com.FileConverter;

@FunctionalInterface
public interface ConvertCommand {

       String execute(String file1, String file2);
}
