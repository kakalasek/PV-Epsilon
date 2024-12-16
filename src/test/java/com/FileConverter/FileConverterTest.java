package com.FileConverter;


import com.Exceptions.JsonReadException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class FileConverterTest{


    private final ClassLoader classloader = getClass().getClassLoader();

    @Test
    public void jsonHandlingExceptionThrownWhenCsvWithRecordWhichDoesNotHaveAllFields() throws JsonReadException, ExecutionException, InterruptedException {
        String fileToConvert = classloader.getResource("csvFiles/weirdTest.csv").getPath();
        String outputFile = classloader.getResource("jsonFiles/output2.json").getPath();
        ConvertCommand convertCommand = new ConvertFromCsvToJsonCommand();
        FileConverter fileConverter = new FileConverter();

        fileConverter.executeCommand(convertCommand, fileToConvert, outputFile);
        assertEquals("There has been a problem when creating the new json file", fileConverter.getInitiatedFileConversions().get(0).get());
    }

    @Test
    public void jsonHandlingExceptionThrownWhenJsonWhichDoesNotHaveAllFieldsSpecifiedInFirstObjectInOtherObjects() throws ExecutionException, InterruptedException {
        String fileToConvert = classloader.getResource("jsonFiles/weirdTest.json").getPath();
        String outputFile = classloader.getResource("csvFiles/output2.csv").getPath();
        ConvertCommand convertCommand = new ConvertFromJsonToCsvCommand();
        FileConverter fileConverter = new FileConverter();

        fileConverter.executeCommand(convertCommand, fileToConvert, outputFile);
        assertEquals("There has been a problem when creating the new csv file", fileConverter.getInitiatedFileConversions().get(0).get());
    }
}