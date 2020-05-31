package com.wangyousong.concurrency.ch8.recipe12;

public class ConsumerData {

    private Consumer consumer;
    private MySubscription subscription;

    /**
     * @return the consumer
     */
    public Consumer getConsumer() {
        return consumer;
    }

    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    /**
     * @return the subscription
     */
    public MySubscription getSubscription() {
        return subscription;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(MySubscription subscription) {
        this.subscription = subscription;
    }


}
