package com.wangyousong.concurrency.ch11.recipe03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskLock implements Runnable {

    private final Lock lock;
    private int number;

    public TaskLock() {
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            lock.lock();
            number = i;
            lock.unlock();
        }
//        System.out.println("TaskLock : " + Thread.currentThread().getName() + " : " + number);
    }


}
