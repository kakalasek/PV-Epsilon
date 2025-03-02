package com.EventLoop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class SettingsTest {

    @After
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field instance = Settings.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(new Settings(), null);
        instance.setAccessible(false);
    }

    @Test
    public void creatingSettingsWorksFine(){
        Settings settings = Settings.loadSettings();
        assertEquals(",", settings.getCsvDelimiter());
    }

    @Test
    public void settingsCsvDelimiterActuallySetsIt(){
        Settings settings = Settings.loadSettings();
        settings.setCsvDelimiter(";");
        assertEquals(";", settings.getCsvDelimiter());
    }
}