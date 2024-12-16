package com.EventLoop;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class EventLoopTest {

    void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void selectingAValidOptionReturnsThatOption(){
        EventLoop eventLoop = new EventLoop();
        provideInput("3");
        assertEquals(Integer.valueOf(3), eventLoop.selectOption.apply(new Scanner(System.in)));
    }

    @Test
    public void selectingAnInvalidOptionStringThrowsInputMismatchException(){
        EventLoop eventLoop = new EventLoop();
        provideInput("String");
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> eventLoop.selectOption.apply(new Scanner(System.in))
        );
    }

    @Test
    public void selectingAnInvalidOptionBlankThrowsInputMismatchException(){
        EventLoop eventLoop = new EventLoop();
        provideInput("\r");
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> eventLoop.selectOption.apply(new Scanner(System.in))
        );
    }
}