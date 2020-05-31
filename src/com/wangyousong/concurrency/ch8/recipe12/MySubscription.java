package com.wangyousong.concurrency.ch8.recipe12;

import java.util.concurrent.Flow.Subscription;

public class MySubscription implements Subscription {

    private boolean canceled = false;
    private long requested = 0;

    @Override
    public void cancel() {
        canceled = true;
    }

    @Override
    public void request(long value) {
        requested += value;
    }

    /**
     * @return the canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * @return the requested
     */
    public long getRequested() {
        return requested;
    }

    public void decreaseRequested() {
        requested--;
    }


}
