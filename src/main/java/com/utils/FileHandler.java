package com.utils;

import com.EventLoop.Settings;
import com.Exceptions.CantLoadSettingsException;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static ArrayList<String[]> readCsv(String filePath) throws CantLoadSettingsException, IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String[]> outputList = new ArrayList<>();
            String newLine;
            Settings settings = Settings.loadSettings();
            String delimiter = settings.getCsvDelimiter();

            while ((newLine = bufferedReader.readLine()) != null) {
                outputList.add(newLine.split(delimiter));
            }

            return outputList;
        }
    }

    public static void writeJson(String filePath, String inputData) throws IOException{
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

            bufferedWriter.write(inputData);
        }
    }
}
