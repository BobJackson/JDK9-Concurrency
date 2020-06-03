package com.wangyousong.concurrency.ch7.recipe08;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Main class of the example. Execute 100 incrementers and 100 decrementers
 * and checks that the results are the expected
 * <p>
 * CAS:
 * 优点：
 * （1）None Deadlock; (synchronized)
 * （2）don't have to execute the code necessary to get and release the lock. (Lock)
 * <p>
 * 缺点：This mechanism also has its drawbacks.
 * （1）Operations must be free from any side effects as they might be retried using livelocks with
 * highly contended resources;
 * （2） they are also harder to monitor for performance when
 * compared with standard locks.
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        final int THREADS = 100;
        /**
         * Atomic array whose elements will be incremented and decremented
         */
        AtomicIntegerArray vector = new AtomicIntegerArray(1000);
        /*
         * An incrementer task
         */
        Incrementer incrementer = new Incrementer(vector);
        /*
         * A decrementer task
         */
        Decrementer decrementer = new Decrementer(vector);

        /*
         * Create and execute 100 incrementer and 100 decrementer tasks
         */
        Thread[] threadIncrementer = new Thread[THREADS];
        Thread[] threadDecrementer = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threadIncrementer[i] = new Thread(incrementer);
            threadDecrementer[i] = new Thread(decrementer);

            threadIncrementer[i].start();
            threadDecrementer[i].start();
        }

        /*
         * Wait for the finalization of all the tasks
         */
        for (int i = 0; i < THREADS; i++) {
            try {
                threadIncrementer[i].join();
                threadDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
         * Write the elements different from 0
         */
        int errors = 0;
        for (int i = 0; i < vector.length(); i++) {
            if (vector.get(i) != 0) {
                System.out.println("Vector[" + i + "] : " + vector.get(i));
                errors++;
            }
        }

        if (errors == 0) {
            System.out.print("No errors found\n");
        }

        System.out.println("Main: End of the example");
    }

}
