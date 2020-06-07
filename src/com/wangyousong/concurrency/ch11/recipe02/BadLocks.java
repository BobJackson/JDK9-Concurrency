package com.wangyousong.concurrency.ch11.recipe02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
/*
(1) If you have to get control of more than one lock in different operations,
try to lock them in the same order in all methods.
(2) Then, release them in inverse order and encapsulate the locks and their
unlocks in a single class. This is so that you don't have
synchronization-related code distributed throughout the code.
 */
public class BadLocks {

    private final Lock lock1;
    private final Lock lock2;

    public BadLocks(Lock lock1, Lock lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    public void operation1() {
        lock1.lock();
        lock2.lock();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
            lock1.unlock();
        }

    }

    public void operation2() {
        lock2.lock();
        lock1.lock();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
            lock2.unlock();
        }

    }

}
