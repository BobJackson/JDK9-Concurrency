package com.wangyousong.concurrency.ch11.recipe08;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

    private final int[] array;

    public Task(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length; i++) {
            array[i]++;
            try {
                TimeUnit.MILLISECONDS.sleep(1);
//                TimeUnit.NANOSECONDS.sleep(100_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
