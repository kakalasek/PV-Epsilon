package com.FileConverter;

import com.Exceptions.CsvReadException;
import com.utils.FileHandler;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ConvertFromJsonToCsvCommandTest{

    private final ClassLoader classloader = getClass().getClassLoader();

    @Test
    public void conversionFromJsonToCsvWorksFine() throws CsvReadException {
        String fileToConvert = classloader.getResource("jsonFiles/test1.json").getPath();
        String outputFile = classloader.getResource("csvFiles/output1.csv").getPath();

        ConvertCommand convertCsvToJson = new ConvertFromJsonToCsvCommand();
        convertCsvToJson.execute(fileToConvert, outputFile);

        ArrayList<String[]> outputCsv = FileHandler.readCsv(outputFile);
        ArrayList<String[]> expectedCsv = new ArrayList<>();
        expectedCsv.add(new String[]{"name", "surname"});
        expectedCsv.add(new String[]{"Peter", "Parker"});
        expectedCsv.add(new String[]{"John", "Cena"});

        assertEquals(expectedCsv.getFirst(), outputCsv.getFirst());
        assertEquals(expectedCsv.get(1), outputCsv.get(1));
        assertEquals(expectedCsv.get(2), outputCsv.get(2));
    }
}