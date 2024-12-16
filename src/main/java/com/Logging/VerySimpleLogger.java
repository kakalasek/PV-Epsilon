package com.Logging;

import com.EventLoop.Settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class VerySimpleLogger {

    public VerySimpleLogger(){

    }

    public void log(String message) {
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("epsilon.log", true))){
            LocalDateTime time = LocalDateTime.now();

            String log = " [" + time + "] " + message + "\n";

            bf.write(log);
        } catch (IOException e){
            System.out.println("Check your log file, logging ain't working");
        }
    }
}
