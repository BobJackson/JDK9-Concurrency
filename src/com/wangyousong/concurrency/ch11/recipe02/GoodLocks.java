package com.wangyousong.concurrency.ch11.recipe02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
/*
Using this rule, you will avoid deadlock situations.


You can try to get all the locks that you need to do the operation using the
tryLock() method. If you can't get control of one of the locks, you must release all the
locks that you may have had and start the operation again.
 */
public class GoodLocks {
    private final Lock lock1;
    private final Lock lock2;

    public GoodLocks(Lock lock1, Lock lock2) {
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
}
