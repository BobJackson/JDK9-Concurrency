package com.wangyousong.concurrency.ch7.recipe09;

import java.util.Date;

public class Task implements Runnable {

    private final Flag flag;

    public Task(Flag flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        int i = 0;
        while (flag.flag) {
            i++;
        }
        System.out.printf("Task: %d stopped- %s\n", i, new Date());
    }

}
