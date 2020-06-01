package com.wangyousong.concurrency.ch4.recipe09;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * This class will take the results of the ReportGenerator tasks executed through
 * a CompletinoService
 */
public class ReportProcessor implements Runnable {

    /**
     * CompletionService that executes the ReportGenerator tasks
     */
    private final CompletionService<String> service;
    /**
     * Variable to store the status of the Object. It will executes until the variable
     * takes the true value
     */
    private volatile boolean end;

    /**
     * Constructor of the class. It initializes the attributes of the class
     *
     * @param service The CompletionService used to execute the ReportGenerator tasks
     */
    public ReportProcessor(CompletionService<String> service) {
        this.service = service;
        end = false;
    }

    /**
     * Main method of the class. While the variable end is false, it
     * calls the poll method of the CompletionService and waits 20 seconds
     * for the end of a ReportGenerator task
     */
    @Override
    public void run() {
        while (!end) {
            try {
                // poll() method -> If the queue is empty, it returns null immediately. Otherwise, it returns its first element and removes it from the queue.
                // poll(long timeout, TimeUnit unit) -> Retrieves and removes the Future representing the next completed task, waiting if necessary up to the specified wait time if none are yet present.
                // take() ->  If it is empty, it blocks the thread until the queue has an element. If the queue has elements, it returns and deletes its first element from the queue.
                Future<String> result = service.poll(20, TimeUnit.SECONDS);
                if (result != null) {
                    String report = result.get();
                    System.out.printf("ReportProcessor: Report Received: %s\n", report);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.print("ReportProcessor: End\n");
    }

    /**
     * Method that establish the value of the end attribute
     *
     *
     */
    public void stopProcessing() {
        this.end = true;
    }


}
