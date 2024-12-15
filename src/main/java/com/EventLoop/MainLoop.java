package com.EventLoop;

import com.Exceptions.CantLoadSettingsException;
import com.FileConverter.ConvertCommand;
import com.FileConverter.ConvertFromCsvToJsonCommand;
import com.FileConverter.ConvertFromJsonToCsvCommand;
import com.FileConverter.FileConverter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class MainLoop {

    private final String helloMessage = """
            
            Hello, welcome to this simple file conversion app. What would you like to do?
            
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

    private final Scanner sc;
    private final FileConverter fileConverter;
    private final SettingsLoop settingsLoop;
    private final Function<Scanner, Integer> selectOption;

    public MainLoop(Scanner sc, FileConverter fileConverter, Function<Scanner, Integer> selectOption) throws CantLoadSettingsException {
        this.sc = sc;
        this.fileConverter = fileConverter;
        this.selectOption = selectOption;
        this.settingsLoop = new SettingsLoop(sc, selectOption);
    }

    /**
     * Prints the status of each initiated task to the console
     * @param fileConverter The FileConverter object, which is responsible for the initiated task
     */
    private void showTasksStatus(FileConverter fileConverter){
        for (CompletableFuture<String> task : fileConverter.getInitiatedFileConversions()){
            try {
                if (task.isDone()) {
                    System.out.println(task.get());
                } else {
                    System.out.println("Not done yet");
                }
            } catch (ExecutionException e){
                System.out.println("Conversion ended with an unexpected error. Check the logs for more information");
            } catch (InterruptedException e){
                System.out.println("The conversion is occupied at the moment");
            }
        }
    }

    /**
     * Calls the conversion command and some prompts
     * @param sc The scanner which handler the user input
     * @param fileConverter The FileConverter object which handler the conversion
     * @param prompt1 Prompt for the first file path
     * @param prompt2 Prompt for the second file path
     * @param convertCommand The conversion command
     */
    private void convert(Scanner sc, FileConverter fileConverter, String prompt1, String prompt2, ConvertCommand convertCommand){
        String file1, file2;

        System.out.println(prompt1);
        file1 = sc.nextLine();

        System.out.println(prompt2);
        file2 = sc.nextLine();

        fileConverter.executeCommand(convertCommand, file1, file2);
    }

    /**
     * Starts the main event loop
     */
    public void startLoop() {
        boolean anotherOne = true;

        System.out.println(helloMessage);

        while (anotherOne) {
            try {
                System.out.print(prompt);

                int userSelect = selectOption.apply(sc);

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
                    case 4 -> settingsLoop.startLoop();
                    case 5 -> anotherOne = false;
                    default -> throw new InputMismatchException("Your pick bust be a number of one of the options!");
                }
            } catch (InputMismatchException _){

            }
        }
    }
}
