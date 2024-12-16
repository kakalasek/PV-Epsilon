package com.utils;

import com.EventLoop.Settings;
import com.Exceptions.*;
import com.Logging.VerySimpleLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Special class which offers method to read and write a json or csv file
 */
public class FileHandler {

    private static final VerySimpleLogger logger = new VerySimpleLogger();

    /**
     * Reads a csv file.
     * @param filePath Path to the csv file
     * @return An ArrayList object with String arrays as its elements. The first String array will contain the field names,
     * every other String array will represent one record inside the csv file.
     * @throws CsvReadException Gets thrown if there is any problem reading the provided csv file
     */
    public static ArrayList<String[]> readCsv(String filePath) throws CsvReadException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String[]> outputList = new ArrayList<>();
            String newLine;
            Settings settings = Settings.loadSettings();
            String delimiter = settings.getCsvDelimiter();

            while ((newLine = bufferedReader.readLine()) != null) {
                outputList.add(newLine.split(delimiter));
            }

            return outputList;
        } catch (IOException e) {
            logger.log(e.toString());
            throw new CsvReadException("There has been a problem reading the csv file", e);
        }
    }

    /**
     * Reads a json file.
     * @param filePath Path to the json file
     * @return A String representation of the json files content
     * @throws JsonReadException Thrown if there is any problem reading the provided json file
     */
    public static String readJson(String filePath) throws JsonReadException{
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode recordsList = objectMapper.readTree(new File(filePath));

            return recordsList.toString();

        } catch (IOException e){
            throw new JsonReadException("There has been a problem reading your json file", e);
        }
    }

    /**
     * Writes json to a file. Simply writes the provided string
     * @param filePath The file to write to
     * @param inputData The data to write
     * @throws JsonWriteException Is thrown if there is any problem writing to the specified file
     */
    public static void writeJson(String filePath, String inputData) throws JsonWriteException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write("");

            bufferedWriter.write(inputData);
        } catch (IOException e){
            throw new JsonWriteException("There has been a problem writing the json to the file", e);
        }
    }

    /**
     * Writes csv to a file. Takes care of parsing the data
     * @param filepath The file to write to
     * @param inputData The data to write. The ArrayList holds all the csv records represented as String arrays
     *                  The first ArrayList entry holds the csv field names, other entries hold the csv records
     * @throws CsvWriteException Is thrown if any problem with writing to the specified file appears
     */
    public static void writeCsv(String filepath, ArrayList<String[]> inputData) throws CsvWriteException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))){
            Settings settings = Settings.loadSettings();
            String delimiter = settings.getCsvDelimiter();

            bufferedWriter.write("");

            for(String[] record : inputData){
                Arrays.parallelSetAll(record, (i) -> record[i].trim());
                String recordAsString = Arrays.toString(record).replace(", ", delimiter)
                                                                .replace("[", "")
                                                                .replace("]", "");

                recordAsString = recordAsString + "\n";
                bufferedWriter.append(recordAsString);
            }
        } catch (IOException e){
            throw new CsvWriteException("There has been a problem with writing the csv to the file", e);
        }
    }
}
