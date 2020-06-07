package com.wangyousong.concurrency.ch11.recipe04;

import java.util.concurrent.locks.Lock;

public class Task2 implements Runnable {

    private final Lock lock;

    public Task2(Lock lock) {
        this.lock = lock;
    }

    /*
    When you need to implement a block of code protected by a lock, analyze it carefully to
    only include necessary instructions. Split the method into various critical sections, and use
    more than one lock if necessary to get the best performance of your application
     */
    @Override
    public void run() {
        lock.lock();
        Operations.readData();
        lock.unlock();
        Operations.processData();
        lock.lock();
        Operations.writeData();
        lock.unlock();
    }
}
