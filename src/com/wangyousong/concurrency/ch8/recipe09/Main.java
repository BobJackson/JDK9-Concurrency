package com.wangyousong.concurrency.ch8.recipe09;

import java.util.concurrent.TimeUnit;

/**
 * Main class of the example.
 * <p>
 * Elements of LinkedTransferQueue are stored in the same order as they arrive, so the ones
 * that arrived earlier are consumed first. It may be the case when you want to develop a
 * producer/consumer program, where data is consumed according to some priority instead of
 * arrival time. In this recipe, you will learn how to use PriorityBlockingQueue to implement
 * a data structure to be used in the producer/consumer problem whose elements will be
 * ordered by priority; elements with higher priority will be consumed first.
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        /*
         * Create a Prioriy Transfer Queue
         */
        MyPriorityTransferQueue<Event> buffer = new MyPriorityTransferQueue<>();

        /*
         * Create a Producer object
         */
        Producer producer = new Producer(buffer);

        /*
         * Launch 10 producers
         */
        Thread[] producerThreads = new Thread[10];
        for (int i = 0; i < producerThreads.length; i++) {
            producerThreads[i] = new Thread(producer);
            producerThreads[i].start();
        }

        /*
         * Create and launch the consumer
         */
        Consumer consumer = new Consumer(buffer);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        /*
         * Write in the console the actual consumer count
         */
        System.out.printf("Main: Buffer: Consumer count: %d\n", buffer.getWaitingConsumerCount());

        /*
         * Transfer an event to the consumer
         */
        Event myEvent = new Event("Core Event", 0);
        buffer.transfer(myEvent);
        System.out.print("Main: My Event has ben transfered.\n");

        /*
         * Wait for the finalization of the producers
         */
        for (Thread producerThread : producerThreads) {
            producerThread.join();
        }

        /*
         * Sleep the thread for one second
         */
        TimeUnit.SECONDS.sleep(1);

        /*
         * Write the actual consumer count
         */
        System.out.printf("Main: Buffer: Consumer count: %d\n", buffer.getWaitingConsumerCount());

        /*
         * Transfer another event
         */
        myEvent = new Event("Core Event 2", 0);
        buffer.transfer(myEvent);

        /*
         * Wait for the finalization of the consumer
         */
        consumerThread.join();

        /*
         * Write a message indicating the end of the program
         */
        System.out.print("Main: End of the program\n");
    }

}
