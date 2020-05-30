package com.wangyousong.concurrency.ch2.recipe01.problem;

public class ParkingCash {

	private static final int cost = 2;

	private long cash;

	public ParkingCash() {
		cash = 0;
	}

	public void vehiclePay() {
		cash += cost;
	}

	public void close() {
		System.out.print("Closing accounting");
		long totalAmount = cash;
		cash = 0;
		System.out.printf("The total amount is : %d", totalAmount);
	}
}
