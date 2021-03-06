package com.wangyousong.concurrency.ch11.recipe04;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Task1 task1 = new Task1(lock);
        Task2 task2 = new Task2(lock);
        Thread[] threads = new Thread[10];

        Date begin, end;

        begin = new Date();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task1);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = new Date();
        // Main: First Approach: 30028
        System.out.printf("Main: First Approach: %d\n", (end.getTime() - begin.getTime()));

        begin = new Date();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task2);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = new Date();
        // Main: Second Approach: 10019
        System.out.printf("Main: Second Approach: %d\n", (end.getTime() - begin.getTime()));


    }

}
