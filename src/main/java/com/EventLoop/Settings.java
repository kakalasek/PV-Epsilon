package com.EventLoop;

/**
 * This class  represents the settings of this app. It loads them from a special file called settings.json
 */
public class Settings {

    private String csvDelimiter;
    private static Settings instance = null;

    public static Settings loadSettings() {
        if(instance == null){
                instance = new Settings();
        }
        return instance;
    }

    public Settings(){
        this.csvDelimiter = ",";
    }

    public String getCsvDelimiter() {
        return csvDelimiter;
    }

    public void setCsvDelimiter(String csvDelimiter){
            this.csvDelimiter = csvDelimiter;
    }
}
