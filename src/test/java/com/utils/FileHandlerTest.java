package com.utils;

import com.Exceptions.CsvReadException;
import com.Exceptions.CsvWriteException;
import com.Exceptions.JsonReadException;
import com.Exceptions.JsonWriteException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FileHandlerTest {

    private final ClassLoader classloader = getClass().getClassLoader();

    @Test
    public void readingNonExistentFileThrowsCsvReadException(){
        assertThrows(CsvReadException.class, () -> {
            FileHandler.readCsv("csvFiles/nonExistentFile");
        });
    }

    @Test
    public void readingNonExistentFileThrowsJsonReadException(){
        assertThrows(JsonReadException.class, () -> {
            FileHandler.readJson("jsonFiles/nonExistentFile");
        });
    }

    @Test
    public void readingInvalidJsonFileThrowsJsonReadException(){
        String invalidJsonFilePath = classloader.getResource("jsonFiles/invalidJsonFile.json").getPath();
        assertThrows(JsonReadException.class, () -> {
            FileHandler.readJson(invalidJsonFilePath);
        });
    }

    @Test
    public void ReadWriteToJsonFileWorksFine() throws JsonWriteException, JsonReadException {
        String fileToWritePath = classloader.getResource("jsonFiles/jsonToWrite.json").getPath();
        String jsonToWrite = "[{\"name\":\"Bigus\",\"surname\":\"Dickus\"}]";
        FileHandler.writeJson(fileToWritePath, jsonToWrite);
        String loadedJson = FileHandler.readJson(fileToWritePath);
        assertEquals(jsonToWrite,loadedJson);
    }

    @Test
    public void writingToNonExistentFileThrowsJsonWriteException(){
        assertThrows(JsonWriteException.class, () -> {
            FileHandler.writeJson("jsonFiles/nonExistentJsonFile", "[{\"data\":\"data\"}]");
        });
    }

    @Test
    public void readWriteToCsvFileWorksFine() throws CsvWriteException, CsvReadException {
        String fileToWritePath = classloader.getResource("csvFiles/csvToWrite.csv").getPath();
        ArrayList<String[]> csvToWrite = new ArrayList<>();

        csvToWrite.add(new String[]{"name", "surname", "number"});
        csvToWrite.add(new String[]{"Peter", "Parker", "234 323 123"});
        csvToWrite.add(new String[]{"John", "Cena", "112 34221 553"});
        csvToWrite.add(new String[]{"Ondrej", "Mandik", "101 010 101"});

        FileHandler.writeCsv(fileToWritePath, csvToWrite);

        ArrayList<String[]> loadedCsv = FileHandler.readCsv(fileToWritePath);
        assertEquals(csvToWrite.getFirst(), loadedCsv.getFirst());
        assertEquals(csvToWrite.get(1), loadedCsv.get(1));
        assertEquals(csvToWrite.get(2), loadedCsv.get(2));
        assertEquals(csvToWrite.get(3), loadedCsv.get(3));
    }

    @Test
    public void writingToNonExistentFileThrowsCsvWriteException(){
        ArrayList<String[]> csvToWrite = new ArrayList<>();

        csvToWrite.add(new String[]{"name", "surname", "number"});
        csvToWrite.add(new String[]{"Peter", "Parker", "234 323 123"});
        csvToWrite.add(new String[]{"John", "Cena", "112 34221 553"});
        csvToWrite.add(new String[]{"Ondrej", "Mandik", "101 010 101"});

        assertThrows(CsvWriteException.class, () -> {
            FileHandler.writeCsv("csvFiles/nonExistentCsvFile", csvToWrite);
        });
    }
}