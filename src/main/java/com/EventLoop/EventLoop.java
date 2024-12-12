package com.EventLoop;

import com.FileConverter.ConvertFromCsvToJsonCommand;
import com.FileConverter.ConvertFromJsonToCsvCommand;
import com.FileConverter.FileConverter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.FileConverter.ConvertCommand.IOERROR;
import static com.FileConverter.ConvertCommand.SUCCESS;

public class EventLoop implements Runnable{

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

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        FileConverter fileConverter = new FileConverter();
        boolean anotherOne = true;
        String file1, file2;
        String prompt = """
                
                Select one of these options
                ===========================
                1) Convert CSV to JSON
                2) Convert JSON to CSV
                3) Show Conversion Progress
                4) Exit
                
                """;

        System.out.println("Hello, welcome to this simple file conversion app. What would you like to do?");

        while (anotherOne) {
            try{
                System.out.print(prompt);
                String userSelect = sc.nextLine();
                if (!userSelect.matches("\\d")){
                    throw new InputMismatchException();
                }

                switch (Integer.parseInt(userSelect)) {
                    case 1 -> {
                        System.out.println("Enter a valid file URL for your CSV file");
                        file1 = sc.nextLine();
                        System.out.println("Enter a valid file URL for your output file");
                        file2 = sc.nextLine();
                        fileConverter.executeCommand(new ConvertFromCsvToJsonCommand(), file1, file2);
                    }
                    case 2 -> {
                        System.out.println("Enter a valid file URL for your JSON file");
                        file1 = sc.nextLine();
                        System.out.println("Enter a valid file URL for your output file");
                        file2 = sc.nextLine();
                        fileConverter.executeCommand(new ConvertFromJsonToCsvCommand(), file1, file2);
                    }
                    case 3 -> showTasksStatus(fileConverter);
                    case 4 -> anotherOne = false;
                    default -> throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Your pick bust be a number in this range: 1-4!");
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (!anotherOne) { sc.close(); };
            }
        }
    }
}

