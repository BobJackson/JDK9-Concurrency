package com.wangyousong.concurrency.ch11.recipe07;

import com.wangyousong.concurrency.ch11.recipe07.DBConnectionOK;

public class Task implements Runnable {

    @Override
    public void run() {

        System.out.printf("%s: Getting the connection...\n", Thread.currentThread().getName());
        DBConnectionOK connection = DBConnectionOK.getConnection();
        System.out.printf("%s: End\n", Thread.currentThread().getName());
    }

}
