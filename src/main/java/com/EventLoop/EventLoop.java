package com.EventLoop;

import com.Exceptions.CantLoadSettingsException;
import com.FileConverter.ConvertCommand;
import com.FileConverter.ConvertFromCsvToJsonCommand;
import com.FileConverter.ConvertFromJsonToCsvCommand;
import com.FileConverter.FileConverter;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.FileConverter.ConvertCommand.IOERROR;
import static com.FileConverter.ConvertCommand.SUCCESS;

public class EventLoop implements Runnable{

    private final String helloMessage = """
            
            Hello, welcome to this simple file conversion app. What would you like to do?
            
            """;

    private final String settingsMessage = """
           
            Welcome to application settings. What would you like to change?
            
            """;

    private final String prompt = """
            +-----------------------------+
            | Select one of these options |
            | =========================== |
            | 1) Convert CSV to JSON      |
            | 2) Convert JSON to CSV      |
            | 3) Show Conversion Progress |
            | 4) Settings                 |
            | 5) Exit                     |
            +-----------------------------+
            
            """;

    private final String settingsPrompt = """
            +-----------------------------+
            | Settings                    |
            | =========================== |
            | 1) Change Csv Delimiter     |
            | 2) Back                     |
            +-----------------------------+
            
            """;

    /**
     * Prints the status of each initiated task to the console
     * @param fileConverter The FileConverter object, which is responsible for the initiated task
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void showTasksStatus(FileConverter fileConverter) throws ExecutionException, InterruptedException {
        for (CompletableFuture<Integer> task : fileConverter.getInitiatedFileConversions()){
            if (task.isDone()) {
                switch (task.get()){
                    case SUCCESS -> System.out.println("Successfully Converted");
                    case IOERROR -> System.out.println("An IOError Has Occurred");
                }
            } else {
                System.out.println("Not done yet");
            }
        }
    }

    /**
     * Lets the user type a number corresponding to his desired option
     * @param sc The Scanner object responsible for loading the user input
     * @return Users selection parsed as an Integer
     * @throws InputMismatchException If the user types something else than one of the allowed numbers
     */
    private int selectOption(Scanner sc) throws InputMismatchException{
        String userSelect = sc.nextLine();

        if (!userSelect.matches("\\d")){
            throw new InputMismatchException("Your pick bust be a number of one of the options!");
        }

        return Integer.parseInt(userSelect);
    }

    /**
     * Works similar to the main event loop, but is used for settings
     * @param sc The Scanner object responsible for loading the user input
     * @throws CantLoadSettingsException If the program is not able to load the settings
     */
    private void settingsLoop(Scanner sc) throws CantLoadSettingsException {
        try {
            boolean anotherOne = true;
            Settings settings = Settings.loadSettings();

            System.out.println(settingsMessage);

            while (anotherOne) {
                System.out.println(settingsPrompt);

                int userSelect = selectOption(sc);

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

    private void convert(Scanner sc, FileConverter fileConverter, String prompt1, String prompt2, ConvertCommand convertCommand){
        String file1, file2;

        System.out.println(prompt1);
        file1 = sc.nextLine();

        System.out.println(prompt2);
        file2 = sc.nextLine();

        fileConverter.executeCommand(convertCommand, file1, file2);
    }

    private void mainEventLoop(Scanner sc, FileConverter fileConverter){
        boolean anotherOne = true;

        System.out.println(helloMessage);

        while (anotherOne) {
            try{
                System.out.print(prompt);

                int userSelect = selectOption(sc);

                switch (userSelect) {
                    case 1 -> convert(sc,
                            fileConverter,
                            "Enter a valid file URL for your CSV file",
                            "Enter a valid file URL for your output file",
                            new ConvertFromCsvToJsonCommand());
                    case 2 -> convert(sc,
                            fileConverter,
                            "Enter a valid file URL for your JSON file",
                            "Enter a valid file URL for your output file",
                            new ConvertFromJsonToCsvCommand());
                    case 3 -> showTasksStatus(fileConverter);
                    case 4 -> settingsLoop(sc);
                    case 5 -> anotherOne = false;
                    default -> throw new InputMismatchException("Your pick bust be a number of one of the options!");
                }

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (ExecutionException | InterruptedException | CantLoadSettingsException e) {
                throw new RuntimeException(e);
            } finally {
                if (!anotherOne) { sc.close(); };
            }
        }
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        FileConverter fileConverter = new FileConverter();

        mainEventLoop(sc, fileConverter);
    }
}

