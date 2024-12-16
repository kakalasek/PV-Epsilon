package com.FileConverter;


import com.Exceptions.JsonReadException;
import com.utils.FileHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConvertFromCsvToJsonCommandTest {

    private final ClassLoader classloader = getClass().getClassLoader();

    @Test
    public void conversionFromCsvToJsonWorks() throws JsonReadException {
        String fileToConvert = classloader.getResource("csvFiles/test1.csv").getPath();
        String outputFile = classloader.getResource("jsonFiles/output1.json").getPath();

        ConvertCommand convertCsvToJson = new ConvertFromCsvToJsonCommand();
        convertCsvToJson.execute(fileToConvert, outputFile);

        String outputJson = FileHandler.readJson(outputFile);

        String expectedJson = "[{\"id\":\"1\",\"first_name\":\"Lexy\",\"last_name\":\"Senecaut\",\"email\":\"lsenecaut0@harvard.edu\",\"gender\":\"Female\",\"ip_address\":\"15.210.126.134\"},{\"id\":\"2\",\"first_name\":\"Fredrika\",\"last_name\":\"Helleker\",\"email\":\"fhelleker1@indiegogo.com\",\"gender\":\"Female\",\"ip_address\":\"53.197.22.102\"}]";

        assertEquals(expectedJson, outputJson);
    }

}