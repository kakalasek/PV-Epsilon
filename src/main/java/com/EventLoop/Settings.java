package com.EventLoop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class Settings {

    @JsonProperty private String csvDelimiter;
    private static Settings instance = null;

    public static Settings loadSettings() throws IOException {
        if(instance == null){
            ObjectMapper objectMapper = new ObjectMapper();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            URL settingsFile = classLoader.getResource("settings.json");
            instance = objectMapper.readValue(settingsFile, Settings.class);
        }
        return instance;
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(String delimiter) {
        this.csvDelimiter = delimiter;
    }
}
