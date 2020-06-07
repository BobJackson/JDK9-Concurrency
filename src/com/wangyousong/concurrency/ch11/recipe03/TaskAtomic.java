package com.wangyousong.concurrency.ch11.recipe03;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAtomic implements Runnable {

    private final AtomicInteger number;

    public TaskAtomic() {
        this.number = new AtomicInteger();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            // Main: Atomic results: 414 ,
            number.set(i); // Sets the value to newValue ,with memory effects as specified by VarHandle#setVolatile -> private volatile int value;
            // Main: Atomic results: 832
//            number.compareAndSet(i,i); // Atomically sets the value to newValue if the current value == expectedValue, with memory effects as specified by VarHandle.compareAndSet.
        }
//        System.out.println("TaskAtomic : " + Thread.currentThread().getName() + " : " + number.get());
    }

}
