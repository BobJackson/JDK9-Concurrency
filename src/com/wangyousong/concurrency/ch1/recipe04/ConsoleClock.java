package com.wangyousong.concurrency.ch1.recipe04;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class that writes the actual date to a file every second
 * 
 */
public class ConsoleClock implements Runnable {

	/**
	 * Main method of the class
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.printf("The ConsoleClock has been interrupted.\n");
			}
		}
	}
}