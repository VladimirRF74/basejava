package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threadList.add(thread);
        }
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    private synchronized void inc() {
        counter++;
    }
}