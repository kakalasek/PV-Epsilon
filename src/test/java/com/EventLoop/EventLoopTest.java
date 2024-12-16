package com.EventLoop;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EventLoopTest extends TestCase {

    void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    public void testSelectingAValidOptionReturnsThatOption(){
        EventLoop eventLoop = new EventLoop();
        provideInput("3");
        assertEquals(Integer.valueOf(3), eventLoop.selectOption.apply(new Scanner(System.in)));
    }

    public void testSelectingAnInvalidOptionStringThrowsInputMismatchException(){
        EventLoop eventLoop = new EventLoop();
        provideInput("String");
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> eventLoop.selectOption.apply(new Scanner(System.in))
        );
    }

    public void testSelectingAnInvalidOptionBlankThrowsInputMismatchException(){
        EventLoop eventLoop = new EventLoop();
        provideInput("\r");
        Assertions.assertThrows(
                InputMismatchException.class,
                () -> eventLoop.selectOption.apply(new Scanner(System.in))
        );
    }
}