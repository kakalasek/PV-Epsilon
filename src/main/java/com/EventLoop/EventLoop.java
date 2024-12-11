package com.EventLoop;

import com.FileConverter.ConvertFromCsvToJsonCommand;
import com.FileConverter.ConvertFromJsonToCsvCommand;
import com.FileConverter.FileConverter;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventLoop implements Runnable{

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
                    case 1:
                        System.out.println("Enter a valid file URL for your CSV file");
                        file1 = sc.nextLine();
                        System.out.println("Enter a valid file URL for your output file");
                        file2 = sc.nextLine();
                        fileConverter.executeCommand(new ConvertFromCsvToJsonCommand(), file1, file2);
                        break;
                    case 2:
                        System.out.println("Enter a valid file URL for your JSON file");
                        file1 = sc.nextLine();
                        System.out.println("Enter a valid file URL for your output file");
                        file2 = sc.nextLine();
                        fileConverter.executeCommand(new ConvertFromJsonToCsvCommand(), file1, file2);
                        break;
                    case 3:
                        for (CompletableFuture<Integer> task : fileConverter.getInitiatedFileConversions()){
                            if (task.isDone()) {
                                System.out.println(task.get());
                            } else {
                                System.out.println("Not done yet");
                            }
                        }
                        break;
                    case 4:
                        anotherOne = false;
                        break;
                    default:
                        throw new InputMismatchException();
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

