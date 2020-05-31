package com.wangyousong.concurrency.ch11.recipe09;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            Task task = new Task(lock);
            Thread thread = new Thread(task);
            thread.start();
        }
    }

}
