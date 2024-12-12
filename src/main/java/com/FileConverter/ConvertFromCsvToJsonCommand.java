package com.FileConverter;

import com.Exceptions.CantLoadSettingsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertFromCsvToJsonCommand implements ConvertCommand {

    private Map<String, List<Map<String, String>>> castCsvToMap(List<String[]> csvData){
        Map<String, List<Map<String, String>>> outputMap = new HashMap<>();
        outputMap.put("elements", new ArrayList<>());

        List<Map<String, String>> elements = outputMap.get("elements");
        Map<String, String> currentRecord;
        String[] csvFields = csvData.getFirst();
        int numberOfRecords = csvData.size();
        int numberOfFields = csvFields.length;
        int recordNumber;
        int fieldNumber;


        for(recordNumber = 1; recordNumber < numberOfRecords; recordNumber++){
            elements.add(new HashMap<>());
            currentRecord = elements.get(recordNumber-1);

            for(fieldNumber = 0; fieldNumber < numberOfFields; fieldNumber++){
                String fieldName = csvFields[fieldNumber];
                String fieldDataForRecord = csvData.get(recordNumber)[fieldNumber];

                currentRecord.put(fieldName, fieldDataForRecord);
            }
        }

        return outputMap;
    }

    @Override
    public Integer execute(String file1, String file2) {
        try {
            ArrayList<String[]> loadedCsvData = FileHandler.readCsv(file1);

            Map<String, List<Map<String, String>>> csvDataInHashMap = castCsvToMap(loadedCsvData);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(csvDataInHashMap);

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
