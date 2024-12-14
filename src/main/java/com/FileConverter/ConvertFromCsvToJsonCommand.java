package com.FileConverter;

import com.Exceptions.CantLoadSettingsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertFromCsvToJsonCommand implements ConvertCommand {


    private String createJson(ArrayList<String[]> csvData) throws JsonProcessingException {

        String[] fields = csvData.removeFirst();

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        int fieldNumber;

        for(String[] record : csvData) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            for(fieldNumber = 0; fieldNumber < fields.length; fieldNumber++){
                objectNode.put(fields[fieldNumber], record[fieldNumber]);
            }
            arrayNode.add(objectNode);
        }

        return objectMapper.writeValueAsString(arrayNode);
    }

    @Override
    public Integer execute(String file1, String file2) {
        try {
            ArrayList<String[]> loadedCsvData = FileHandler.readCsv(file1);

            String jsonData = createJson(loadedCsvData);

            FileHandler.writeJson(file2, jsonData);

        } catch (IOException e){
            e.printStackTrace();
            return IOERROR;
        } catch (CantLoadSettingsException e) {
            throw new RuntimeException(e);
        }
        return SUCCESS;
    }
}
