package com.wangyousong.concurrency.ch4.recipe09;

import java.util.concurrent.*;

/**
 * Main class of the example creates all the necessary objects and throws the tasks
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Create the executor and thee CompletionService using that executor
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        // Crete two ReportRequest objects and two Threads to execute them
        ReportRequest faceRequest = new ReportRequest("Face", service);
        ReportRequest onlineRequest = new ReportRequest("Online", service);
        Thread faceThread = new Thread(faceRequest);
        Thread onlineThread = new Thread(onlineRequest);

        // Create a ReportSender object and a Thread to execute  it
        ReportProcessor processor = new ReportProcessor(service);
        Thread senderThread = new Thread(processor);

        // Start the Threads
        System.out.print("Main: Starting the Threads\n");
        faceThread.start();
        onlineThread.start();
        senderThread.start();

        // Wait for the end of the ReportGenerator tasks
        try {
            System.out.print("Main: Waiting for the report generators.\n");
            faceThread.join();
            onlineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        System.out.print("Main: Shuting down the executor.\n");
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // End the execution of the ReportSender
        processor.stopProcessing();
        System.out.print("Main: Ends\n");

    }

}
