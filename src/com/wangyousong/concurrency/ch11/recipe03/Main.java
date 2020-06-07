package com.wangyousong.concurrency.ch11.recipe03;

import java.util.Date;
/*
In some cases, Java's atomic variables offer a better performance than solutions based on
synchronization mechanisms (specially when we care about atomicity within each separate
variable). Some classes of the java.util.concurrent package use atomic variables
instead of synchronization. In this recipe, you will develop an example that shows how an
atomic attribute provides better performance than synchronization.
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        TaskLock lockTask = new TaskLock();

        int numberThreads = 50;
        Thread[] threads = new Thread[numberThreads];
        Date begin, end;

        begin = new Date();
        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new Thread(lockTask);
            threads[i].start();
        }

        for (int i = 0; i < numberThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = new Date();

        // Main: Lock results: 1359
        System.out.printf("Main: Lock results: %d\n", (end.getTime() - begin.getTime()));

        TaskAtomic atomicTask = new TaskAtomic();
        begin = new Date();
        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new Thread(atomicTask);
            threads[i].start();
        }

        for (int i = 0; i < numberThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = new Date();

        System.out.printf("Main: Atomic results: %d\n", (end.getTime() - begin.getTime()));
    }

}
