package com.EventLoop;

import com.FileConverter.FileConverter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class EventLoop implements Runnable{

    /**
     * Simple function which asks for input and checks if it is number
     */
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

        }
    }
}

