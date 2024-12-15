package com.EventLoop;

import com.Exceptions.CantLoadSettingsException;
import com.FileConverter.ConvertCommand;
import com.FileConverter.ConvertFromCsvToJsonCommand;
import com.FileConverter.ConvertFromJsonToCsvCommand;
import com.FileConverter.FileConverter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class EventLoop implements Runnable{

    public Function<Scanner, Integer> selectOption = (Scanner sc) -> {
        String userSelect = sc.nextLine();

        if (!userSelect.matches("\\d")){
            throw new InputMismatchException("Your pick bust be a number of one of the options!");
        }

        return Integer.parseInt(userSelect);
    };

    @Override
    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            FileConverter fileConverter = new FileConverter();
            MainLoop mainLoop = new MainLoop(sc, fileConverter, selectOption);

            mainLoop.startLoop();

        } catch (CantLoadSettingsException e) {
            System.out.println(e.getMessage());
        }
    }
}

