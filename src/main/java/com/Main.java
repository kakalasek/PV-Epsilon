package com;

import com.EventLoop.EventLoop;

public class Main {
    public static void main(String[] args){
            Thread eventLoop = new Thread(new EventLoop());

            eventLoop.start();
    }
}
