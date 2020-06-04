package com.wangyousong.concurrency.ch8.recipe04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class of the example. Creates a Factory, an Executor using
 * that factory and submits a task to the executor
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        /*
         * Create a new MyThreadFactory object
         */
        MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");

        /*
         * Create a new ThreadPoolExecutor and configure it for use the
         * MyThreadFactoryObject created before as the factory to create the threads
         */
        ExecutorService executor = Executors.newCachedThreadPool(threadFactory);

        // 在此for循环中运行并不能保证一定执行
        /*
        Thread: MyThreadFactory-2:  Creation Date: Thu Jun 04 12:32:33 CST 2020 : Running time: 2005 Milliseconds.
        Thread: MyThreadFactory-3:  Creation Date: Thu Jun 04 12:32:33 CST 2020 : Running time: 2002 Milliseconds.
        Thread: MyThreadFactory-4:  Creation Date: Thu Jun 04 12:32:34 CST 2020 : Running time: 2002 Milliseconds.
        Thread: MyThreadFactory-1:  Creation Date: Thu Jun 04 12:32:32 CST 2020 : Running time: 4006 Milliseconds.
        最后一个线程执行了4006ms
         */
//        for (int i = 0; i < 5; i++) {
//            MyTask task = new MyTask();
//            executor.submit(task);
//            TimeUnit.MILLISECONDS.sleep(500);
//        }

        /*
         * Create a new Task object
         */
        MyTask task = new MyTask();

        /*
         * Submit the task
         */
        executor.submit(task);

        /*
         * Shutdown the executor
         */
        executor.shutdown();

        /*
         * Wait for the finalization of the executor
         */
        executor.awaitTermination(1, TimeUnit.DAYS);

        /*
         * Write a message indicating the end of the program
         */
        System.out.print("Main: End of the program.\n");


    }

}
