package com.wangyousong.concurrency.ch8.recipe03;

/**
 * Main class of the example. Creates a factory, a MyThread object to execute a Task object
 * and executes the Thread
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        /*
         * Create a Factory
         */
        MyThreadFactory myFactory = new MyThreadFactory("MyThreadFactory");

        /*
         * Crate a Task
         */
        MyTask task = new MyTask();

        /*
         * Create a Thread using the Factory to execute the Task
         */
        Thread thread = myFactory.newThread(task);

        /*
         * Start the Thread
         */
        thread.start();

        /*
         * Wait for the finalization of the Thread
         */
        thread.join();

        /*
         * Write the thread info to the console
         */
        System.out.print("Main: Thread information.\n");
        System.out.printf("%s\n", thread);
        System.out.print("Main: End of the example.\n");

    }

}
