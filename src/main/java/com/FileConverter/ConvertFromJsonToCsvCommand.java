package com.FileConverter;

import com.Exceptions.CsvWriteException;
import com.Exceptions.JsonHandlingException;
import com.Exceptions.JsonReadException;
import com.Logging.VerySimpleLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.FileHandler;

import java.util.ArrayList;
import java.util.Iterator;

public class ConvertFromJsonToCsvCommand implements ConvertCommand {

    private final VerySimpleLogger logger = new VerySimpleLogger();

    /**
     * This method creates an ArrayList of String arrays, which represents a csv file and can be written
     * to a file
     * @param jsonData The json String you want to convert to csv
     * @return ArrayList or String arrays which represents a csv file
     * @throws JsonHandlingException Is thrown if any problem occurs during the transition
     */
    private ArrayList<String[]> createCsv(String jsonData) throws JsonHandlingException {
        try {
            ArrayList<String[]> outputList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            int numberOfFields = 0;
            int currentField = 0;
            int currentRecord = 1;
            JsonNode recordsList = objectMapper.readTree(jsonData);
            JsonNode firstObject = recordsList.get(0);
            Iterator<String> fieldIterator = firstObject.fieldNames();
            Iterator<JsonNode> recordIterator = recordsList.elements();

            while (fieldIterator.hasNext()) {
                numberOfFields++;
                fieldIterator.next();
            }

            outputList.add(new String[numberOfFields]);
            fieldIterator = firstObject.fieldNames();

            while (fieldIterator.hasNext()) {
                String fieldName = fieldIterator.next();
                outputList.getFirst()[currentField] = fieldName;
                currentField++;
            }

            while (recordIterator.hasNext()) {
                outputList.add(new String[numberOfFields]);
                JsonNode currentObject = recordIterator.next();
                for (int field = 0; field < numberOfFields; field++) {
                    String currentFieldName = outputList.getFirst()[field];
                    outputList.get(currentRecord)[field] = currentObject.get(currentFieldName).asText();
                }
                currentRecord++;
            }

            return outputList;
        } catch (Exception e){
            throw new JsonHandlingException("There has been a problem when creating the new csv file", e);
        }
    }

    @Override
    public String execute(String file1, String file2) {
        try {
            String loadedJsonData = FileHandler.readJson(file1);

            ArrayList<String[]> csvData = createCsv(loadedJsonData);

            FileHandler.writeCsv(file2, csvData);

        } catch (JsonHandlingException | CsvWriteException | JsonReadException e){
            logger.log(e.toString());
            return e.getMessage();
        }

        return "Successfully converted";
    }
}
