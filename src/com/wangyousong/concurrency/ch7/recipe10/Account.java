package com.wangyousong.concurrency.ch7.recipe10;

public class Account {

	public double amount;

	public double unsafeAmount;

	public Account() {
		this.amount = 0;
		this.unsafeAmount = 0;
	}
}