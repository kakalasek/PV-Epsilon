package com.FileConverter;

import com.EventLoop.Settings;
import com.Exceptions.CantLoadSettingsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.utils.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class ConvertFromJsonToCsvCommand implements ConvertCommand {

    private ArrayList<String[]> createCsv(String jsonData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode arrayNode = objectMapper.readTree(jsonData);
        ArrayList<String[]> outputList = new ArrayList<>();
        int numberOfFields = 0;
        int currentField = 0;
        int currentRecord = 1;
        JsonNode firstObject = arrayNode.get(0);
        Iterator<String> fieldIterator = firstObject.fieldNames();
        Iterator<JsonNode> recordIterator = arrayNode.elements();

        while(fieldIterator.hasNext()){
            numberOfFields++;
            fieldIterator.next();
        }

        outputList.add(new String[numberOfFields]);
        fieldIterator = firstObject.fieldNames();

        while(fieldIterator.hasNext()){
            String fieldName = fieldIterator.next();
            outputList.getFirst()[currentField] = fieldName;
            currentField++;
        }

        while(recordIterator.hasNext()){
            outputList.add(new String[numberOfFields]);
            JsonNode currentObject = recordIterator.next();
            for(int field = 0; field < numberOfFields; field++){
                String currentFieldName = outputList.getFirst()[field];
                outputList.get(currentRecord)[field] = currentObject.get(currentFieldName).asText();
            }
            currentRecord++;
        }

        return outputList;
    }

    @Override
    public Integer execute(String file1, String file2) {
        try{
            String loadedJsonData = FileHandler.readJson(file1);

            ArrayList<String[]> csvData = createCsv(loadedJsonData);

            FileHandler.writeCsv(file2, csvData);

        } catch (IOException e){
            e.printStackTrace();
            return IOERROR;

        } catch (CantLoadSettingsException e) {

        }

        return SUCCESS;
    }
}
