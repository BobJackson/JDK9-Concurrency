package com.wangyousong.concurrency.ch9.recipe07;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Main class of the example. It launch ten Task objects
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Create a Lock
         */
        ReentrantLock lock = new ReentrantLock();

        /*
         * Executes the threads. There is a problem with this
         * block of code. It uses the run() method instead of
         * the start() method.
         */
        for (int i = 0; i < 10; i++) {
            Task task = new Task(lock);
            Thread thread = new Thread(task);
            // Calls to 'run()' should probably be replaced with 'start()'
//            thread.run();
            thread.start();
        }

    }

}
