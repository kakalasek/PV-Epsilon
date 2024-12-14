package com.utils;

import com.EventLoop.Settings;
import com.Exceptions.CantLoadSettingsException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static String readJson(String filePath) throws CantLoadSettingsException, IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode arrayNode = objectMapper.readTree(new File(filePath));

            return arrayNode.toString();
        }
    }

    public static void writeJson(String filePath, String inputData) throws IOException{
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

            bufferedWriter.write(inputData);
        }
    }

    public static void writeCsv(String filepath, ArrayList<String[]> inputData) throws IOException, CantLoadSettingsException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))){
            Settings settings = Settings.loadSettings();
            String delimiter = settings.getCsvDelimiter();

            bufferedWriter.write("");

            for(String[] record : inputData){
                String recordAsString = Arrays.toString(record).replace(",", delimiter)
                                                                .replace("[", "")
                                                                .replace("]", "");
                recordAsString = recordAsString + "\n";
                bufferedWriter.append(recordAsString);
            }
        }
    }
}
