package com.FileConverter;

import com.Exceptions.CsvReadException;
import com.Exceptions.JsonHandlingException;
import com.Exceptions.JsonWriteException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.utils.FileHandler;

import java.util.ArrayList;

public class ConvertFromCsvToJsonCommand implements ConvertCommand {

    /**
     * This method convert representation of csv data to a json String
     * @param csvData The data you want to convert
     * @return The json String created from the csv data
     * @throws JsonHandlingException Is thrown if any problem occurs during the transition
     */
    private String createJson(ArrayList<String[]> csvData) throws JsonHandlingException{
        try {
            String[] fields = csvData.removeFirst();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode arrayNode = objectMapper.createArrayNode();
            int fieldNumber;

            for (String[] record : csvData) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                for (fieldNumber = 0; fieldNumber < fields.length; fieldNumber++) {
                    objectNode.put(fields[fieldNumber], record[fieldNumber]);
                }
                arrayNode.add(objectNode);
            }

            return objectMapper.writeValueAsString(arrayNode);
        } catch (Exception e){
            throw new JsonHandlingException("There has been a problem when creating the new json file", e);
        }
    }

    @Override
    public String execute(String file1, String file2) {
        try {
            ArrayList<String[]> loadedCsvData = FileHandler.readCsv(file1);

            String jsonData = createJson(loadedCsvData);

            FileHandler.writeJson(file2, jsonData);
        } catch (JsonHandlingException | CsvReadException | JsonWriteException e){
            return e.getMessage();
        }

        return "Successfully converted";
    }
}
