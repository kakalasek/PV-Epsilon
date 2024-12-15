package com.EventLoop;

import com.Exceptions.CantLoadSettingsException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class SettingsLoop {

    private final String helloMessage = """
           
            Welcome to application settings. What would you like to change?
            
            """;

    private final String prompt = """
            
            +-----------------------------+
            | Settings                    |
            | =========================== |
            | 1) Change Csv Delimiter     |
            | 2) Back                     |
            +-----------------------------+
            
            """;

    private final Scanner sc;
    private final Function<Scanner, Integer> selectOption;
    private final Settings settings;

    public SettingsLoop(Scanner sc, Function<Scanner, Integer> selectOption) throws CantLoadSettingsException {
        this.sc = sc;
        this.selectOption = selectOption;
        this.settings = Settings.loadSettings();
    }

    /**
     * Works similar to the main event loop, but is used for settings
     */
    public void startLoop() {
        try {
            boolean anotherOne = true;

            System.out.println(helloMessage);

            while (anotherOne) {
                System.out.println(prompt);

                int userSelect = selectOption.apply(sc);

                switch (userSelect) {
                    case 1 -> {
                        System.out.println("Type your new delimiter");
                        String newDelimiter = sc.nextLine();
                        settings.setCsvDelimiter(newDelimiter);
                    }
                    case 2 -> anotherOne = false;
                    default -> throw new InputMismatchException("Your pick bust be a number of one of the options!");
                }
            }
        } catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

}
