package com.wangyousong.concurrency.ch7.recipe09;

import java.util.Date;

import com.wangyousong.concurrency.ch7.recipe09.VolatileFlag;

public class VolatileTask implements Runnable {

    private VolatileFlag flag;

    public VolatileTask(VolatileFlag flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        int i = 0;
        while (flag.flag) {
            i++;
        }
        System.out.printf("VolatileTask: Stoped %d - %s\n", i, new Date());
    }

}
