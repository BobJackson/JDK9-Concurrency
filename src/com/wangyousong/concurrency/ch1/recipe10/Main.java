package com.wangyousong.concurrency.ch1.recipe10;

/**
 * Main class of the example. Creates a Thread factory and creates ten Thread
 * objects using that Factory
 *
 */
public class Main {

	/**
	 * Main method of the example. Creates a Thread factory and creates ten
	 * Thread objects using that Factory
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates the factory
		MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
		// Creates a task
		Task task = new Task();


		// Creates and starts ten Thread objects
		System.out.print("Starting the Threads\n");
		for (int i = 0; i < 10; i++) {
			Thread thread = factory.newThread(task);
			thread.start();
		}
		// Prints the statistics of the ThreadFactory to the console
		System.out.print("Factory stats:\n");
		System.out.printf("%s\n", factory.getStats());

	}

}