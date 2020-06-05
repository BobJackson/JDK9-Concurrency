package com.wangyousong.concurrency.ch8.recipe12;

import java.util.concurrent.Flow.Subscription;

public class MySubscription implements Subscription {

    private boolean canceled = false;
    private long requested = 0;

    @Override
    public void cancel() {
        canceled = true;
    }

    /*
    The subscriber must use
    the request() method of the subscription to indicate that it's ready to process more
    elements from the publisher.
     */
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
