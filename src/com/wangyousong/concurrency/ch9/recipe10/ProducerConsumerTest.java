package com.wangyousong.concurrency.ch9.recipe10;

import edu.umd.cs.mtc.MultithreadedTestCase;

import java.util.concurrent.LinkedTransferQueue;

/**
 * This class implements a test of the LinkedTransferQueue. It has
 * two consumers and a producer. First, arrives the first consumer then
 * arrives the second consumer and finally the producer puts two Strings
 * in the buffer.
 * <p>
 * To control the order of execution of threads, you used the waitForTick() method. This
 * method receives an integer value as a parameter and puts the thread that is executing the
 * method to sleep until all the threads that are running in the test are blocked. When they are
 * blocked, the MultithreadedTC library resumes the threads that are blocked by a call to the
 * waitForTick() method.
 * <p>
 * <p>
 * If the MultithreadedTC library detects that all the threads of the test are blocked except in
 * the waitForTick() method, the test is declared to be in a deadlock state and a
 * java.lang.IllegalStateException exception is thrown
 */
public class ProducerConsumerTest extends MultithreadedTestCase {

    /**
     * Declare the buffer shared by the producer and the consumers
     */
    private LinkedTransferQueue<String> queue;

    /**
     * Creates the buffer
     */
    @Override
    public void initialize() {
        super.initialize();
        queue = new LinkedTransferQueue<>();
        System.out.printf("Test: The test has been initialized%n");
    }

    /**
     * This is the first consumer. It only consumes a String
     *
     * @throws InterruptedException
     */
    public void thread1() throws InterruptedException {
        String ret = queue.take();
        System.out.printf("Thread 1: %s%n", ret);
    }

    /**
     * This is the second consumer. It waits for the first tick that
     * happens when the first consumer arrives. Then, consumes a String
     *
     * @throws InterruptedException
     */
    public void thread2() throws InterruptedException {
        waitForTick(1);
        String ret = queue.take();
        System.out.printf("Thread 2: %s%n", ret);
    }

    /**
     * This is the Producer. It waits for the first tick that happens when
     * the first consumer arrives. Then, waits for the second tick that
     * happens when the second consumer arrives. Finally, put two strings in
     * the buffer.
     */
    public void thread3() {
        waitForTick(1);
        waitForTick(2);
        queue.put("Event 1");
        queue.put("Event 2");
//        queue.put("Event 3");
        System.out.printf("Thread 3: Inserted two elements%n");
    }

    /**
     * This method is executed when the test finish its execution. It checks that
     * the buffer is empty
     */
    @Override
    public void finish() {
        super.finish();
        System.out.printf("Test: End%n");
        assertEquals(0, queue.size()); // if queue.put("Event 3"); junit.framework.AssertionFailedError: expected:<0> but was:<1>
        System.out.printf("Test: Result: The queue is empty%n");
    }
}
