package com.wangyousong.concurrency.ch8.recipe12;

/**
 * This class will be used by the publisher
 * to store the information of every subscriber
 */
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
