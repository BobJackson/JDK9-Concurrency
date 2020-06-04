package com.wangyousong.concurrency.ch8.recipe12;

public class PublisherTask implements Runnable {

    private final ConsumerData consumerData;
    private final News news;

    public PublisherTask(ConsumerData consumerData, News news) {
        this.consumerData = consumerData;
        this.news = news;
    }

    @Override
    public void run() {
        MySubscription subscription = consumerData.getSubscription();
        if (!(subscription.isCanceled() && (subscription.getRequested() > 0))) {
            consumerData.getConsumer().onNext(news);
            subscription.decreaseRequested();
        }
    }
}
