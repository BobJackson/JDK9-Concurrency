package com.wangyousong.concurrency.ch7.recipe09;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
The volatile keyword is important not only because it requires that the writes
are flushed, but also because it ensures that reads are not cached and they fetch the up-to-date value
from the main memory.

Take into account that the volatile keyword guarantees that modifications are written in
the main memory, but its contrary is not always true.(more than one thread and make a lot of modifications)

The volatile keyword only works well when the value of the shared variable is only
modified by one thread. For example, the ++ operator over a volatile variable is not thread-safe.
 */
public class Main {

    public static void main(String[] args) {

        VolatileFlag volatileFlag = new VolatileFlag();
        Flag flag = new Flag();

        VolatileTask vt = new VolatileTask(volatileFlag);
        Task t = new Task(flag);

        Thread thread = new Thread(vt);
        thread.start();
        thread = new Thread(t);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Going to stop volatile task: %s\n", new Date());
        volatileFlag.flag = false;
        System.out.printf("Main: Volatile stop flag changed: %s\n", new Date());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Going to stop task: %s\n", new Date());
        flag.flag = false;
        System.out.printf("Main: Task stopped: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
