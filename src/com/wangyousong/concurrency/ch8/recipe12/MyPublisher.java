package com.wangyousong.concurrency.ch8.recipe12;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class MyPublisher implements Publisher<News> {

    private final Queue<ConsumerData> consumers;
    private final Executor executor;

    public MyPublisher() {
        consumers = new ConcurrentLinkedDeque<>();
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void subscribe(Subscriber<? super News> subscriber) {

        ConsumerData consumerData = new ConsumerData();
        consumerData.setConsumer((Consumer) subscriber);

        MySubscription subscription = new MySubscription();
        consumerData.setSubscription(subscription);

        subscriber.onSubscribe(subscription);

        consumers.add(consumerData);
    }

    public void publish(News news) {
        consumers.forEach(consumerData -> {
            try {
                executor.execute(new PublisherTask(consumerData, news));
            } catch (Exception e) {
                consumerData.getConsumer().onError(e);
            }
        });
    }

}
