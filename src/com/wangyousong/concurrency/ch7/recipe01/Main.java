package com.wangyousong.concurrency.ch7.recipe01;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Main class of the example. First, execute 100 AddTask objects to add 1000000
 * elements to the list and the execute 100 PollTask objects to delete all those
 * elements.
 */
public class Main {

    /**
     * Main method of the class
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        // Create a ConcurrentLinkedDeque to work with it in the example
        // Take into account that the ConcurrentLinkedDeque data structure doesn't allow
        // you to add null values -> final Node<E> newNode = newNode(Objects.requireNonNull(e));
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
        // Create an Array of 100 threads
        Thread[] threads = new Thread[100];

        // Create 100 AddTask objects and execute them as threads
        for (int i = 0; i < threads.length; i++) {
            AddTask task = new AddTask(list);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        System.out.printf("Main: %d AddTask threads have been launched\n", threads.length);

        // Wait for the finalization of the threads
        for (Thread thread : threads) {
            thread.join();
        }

        // Write to the console the size of the list
        System.out.printf("Main: Size of the List: %d\n", list.size());

        // Create 100 PollTask objects and execute them as threads
        for (int i = 0; i < threads.length; i++) {
            PollTask task = new PollTask(list);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        System.out.printf("Main: %d PollTask threads have been launched\n", threads.length);

        // Wait for the finalization of the threads
        for (Thread thread : threads) {
            thread.join();
        }

        // Write to the console the size of the list
        /*
            To write the number of elements in the deque, you used the size() method. You have to
            take into account that this method can return a value that is not real, especially if you use it
            when there are threads adding to or deleting data from the list. The method has to traverse
            the entire deque to count the elements, and the contents of the list can change with this
            operation. Only if you use them when there aren't any threads modifying the deque, you
            will have the guarantee that the returned result would be correct.
         */
        System.out.printf("Main: Size of the List: %d\n", list.size());
    }
}
