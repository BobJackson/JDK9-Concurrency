package com.wangyousong.concurrency.ch10.recipe05;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Main class of the example. Create ten threads to execute ten
 * task objects and write information about the semaphore
 */
public class MainSemaphore {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        /*
         * Create a semaphore
         * A semaphore is a counter that protects the access to one or more shared resources.
         *
         * When a thread wants to use shared resources, it must acquire a semaphore. If the internal
         * counter of the semaphore is greater than 0, the semaphore decrements the counter and
         * allows the access to the shared resource. If the counter of the semaphore is 0, the semaphore
         * blocks the thread until the counter is greater than 0. When the thread has finished using the
         * shared resource, it must release the semaphore. That operation increases the internal
         * counter of the semaphore.
         */
        Semaphore semaphore = new Semaphore(3);

        /*
         * Create an array for ten threads
         */
        Thread[] threads = new Thread[10];

        /*
         * Create and launch a thread every 200 milliseconds. After each creation,
         * show information about the semaphore
         */
        for (int i = 0; i < threads.length; i++) {
            TaskSemaphore task = new TaskSemaphore(semaphore);
            threads[i] = new Thread(task);
            threads[i].start();

            TimeUnit.MILLISECONDS.sleep(200);

            showLog(semaphore);
        }

        /*
         * Write information about the semaphore five times
         */
        for (int i = 0; i < 5; i++) {
            showLog(semaphore);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * Method that writes information about a semaphore
     *
     * @param semaphore Semaphore to write its log information
     */
    private static void showLog(Semaphore semaphore) {
        System.out.printf("********************%n");
        System.out.printf("Main: Semaphore Log%n");
        System.out.printf("Main: Semaphore: Available Permits: %d%n", semaphore.availablePermits());
        System.out.printf("Main: Semaphore: Queued Threads: %s%n", semaphore.hasQueuedThreads());
        System.out.printf("Main: Semaphore: Queue Length: %d%n", semaphore.getQueueLength());
        System.out.printf("Main: Semaphore: Fairness: %s%n", semaphore.isFair());
        System.out.printf("********************%n");
    }

}
