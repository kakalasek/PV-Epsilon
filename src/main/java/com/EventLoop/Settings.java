package com.EventLoop;

import com.Exceptions.CantLoadSettingsException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * This class  represents the settings of this app. It loads them from a special file called settings.json
 */
public class Settings {

    @JsonProperty private String csvDelimiter;
    private static Settings instance = null;

    public static Settings loadSettings() throws CantLoadSettingsException {
        if(instance == null){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                URL settingsFile = classLoader.getResource("settings.json");
                instance = objectMapper.readValue(settingsFile, Settings.class);
            } catch (IOException e){
                throw new CantLoadSettingsException("The program was unable to load the settings file", e);
            }
        }
        return instance;
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(String csvDelimiter) {
        this.csvDelimiter = csvDelimiter;
    }
}
