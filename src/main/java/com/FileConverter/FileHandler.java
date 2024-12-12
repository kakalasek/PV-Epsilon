package com.FileConverter;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static ArrayList<String[]> readCsv(String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String[]> outputList = new ArrayList<>();
            String newLine;
            String splitter = ",";

            while ((newLine = bufferedReader.readLine()) != null) {
                outputList.add(newLine.split(splitter));
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
