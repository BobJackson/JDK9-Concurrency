package com.wangyousong.concurrency.ch1.recipe08;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class that shows the usage of ThreadLocal variables to share data between
 * Thread objects
 *
 */
public class SafeTask implements Runnable {

	/**
	 * ThreadLocal shared between the Thread objects
	 * The Java Concurrency API provides a clean mechanism
	 * called thread-local variables with very good performance. They have some disadvantages
	 * as well. They retain their value while the thread is alive. This can be problematic in
	 * situations where threads are reused
	 */
	private static final ThreadLocal<Date> startDate = ThreadLocal.withInitial(Date::new);

	/**
	 * Main method of the class
	 */
	@Override
	public void run() {
		// Writes the start date
		System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate.get());
		try {
			TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Writes the start date
		System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread().getId(), startDate.get());
	}

}