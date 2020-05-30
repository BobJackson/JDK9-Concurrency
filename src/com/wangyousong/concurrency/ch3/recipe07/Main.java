package com.wangyousong.concurrency.ch3.recipe07;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture: To organize a set of tasks to be executed in a determined order so one or more
 * tasks won't start their execution until others have finished their execution
 */
public class Main {

	public static void main(String[] args) {

		System.out.print("Main: Start\n");
		CompletableFuture<Integer> seedFuture = new CompletableFuture<>();
		Thread seedThread = new Thread(new SeedGenerator(seedFuture));
		seedThread.start();

		System.out.print("Main: Getting the seed\n");
		int seed = 0;
		try {
			seed = seedFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: The seed is: %d\n", seed);

		System.out.print("Main: Launching the list of numbers generator\n");
		NumberListGenerator task = new NumberListGenerator(seed);
		CompletableFuture<List<Long>> startFuture = CompletableFuture.supplyAsync(task);

		System.out.print("Main: Launching step 1\n");
		CompletableFuture<Long> step1Future = startFuture.thenApplyAsync(Main::calculateNearestTo1000);

		System.out.print("Main: Launching step 2\n");
		CompletableFuture<Long> step2Future = startFuture
				.thenApplyAsync(list -> list.stream().max(Long::compare).get());

		CompletableFuture<Void> write2Future = step2Future.thenAccept(selected -> {
			System.out.printf("%s: Step 2: Result - %d\n", Thread.currentThread().getName(), selected);
		});

		System.out.print("Main: Launching step 3\n");
		NumberSelector numberSelector = new NumberSelector();
		CompletableFuture<Long> step3Future = startFuture.thenApplyAsync(numberSelector);

		System.out.print("Main: Waiting for the end of the three steps\n");
		CompletableFuture<Void> waitFuture = CompletableFuture.allOf(step1Future, write2Future, step3Future);
		CompletableFuture<Void> finalFuture = waitFuture.thenAcceptAsync((param) -> {
			System.out.print("Main: The CompletableFuture example has been completed.");
		});

		finalFuture.join();

	}

	private static Long calculateNearestTo1000(List<Long> list) {
		System.out.printf("%s: Step 1: Start\n", Thread.currentThread().getName());
		// calculate the nearest number to 1,000, in a list of random numbers
		long selected = 0;
		long selectedDistance = Long.MAX_VALUE;
		long distance;
		for (Long number : list) {
			distance = Math.abs(number - 1000);
			if (distance < selectedDistance) {
				selected = number;
				selectedDistance = distance;
			}
		}
		System.out.printf("%s: Step 1: Result - %d\n", Thread.currentThread().getName(), selected);
		return selected;
	}


}
