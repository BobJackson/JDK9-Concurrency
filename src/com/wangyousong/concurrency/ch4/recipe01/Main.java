package com.wangyousong.concurrency.ch4.recipe01;

/**
 * Main class of the example. Creates a server and 100 request of the Task class
 * that sends to the server
 */
public class Main {

    /**
     * Main method of the example
     *
     * @param args
     */
    public static void main(String[] args) {
        // Create the server
        Server server = new Server();

        // Send 100 request to the server and finish
        System.out.print("Main: Starting.\n");
        for (int i = 0; i < 100; i++) {
            Task task = new Task("Task " + i);
            server.executeTask(task);
        }

        // Shutdown the executor
        System.out.print("Main: Shuting down the Executor.\n");
        server.endServer();

        // Send a new task. This task will be rejected
        System.out.print("Main: Sending another Task.\n");
        Task task = new Task("Rejected task");
        server.executeTask(task);

        System.out.print("Main: End.\n");

    }

}
