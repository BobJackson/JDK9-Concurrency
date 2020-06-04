package com.wangyousong.concurrency.ch8.recipe12;

import java.util.Date;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        MyPublisher publisher = new MyPublisher();

        Subscriber<News> consumer1, consumer2;
        consumer1 = new Consumer("Consumer 1");
        consumer2 = new Consumer("Consumer 2");

        publisher.subscribe(consumer1);
        publisher.subscribe(consumer2);

        System.out.print("Main: Start\n");

        News news = new News();
        news.setTitle("My first news");
        news.setContent("This is the content");
        news.setDate(new Date());

        publisher.publish(news);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        news = new News();
        news.setTitle("My second news");
        news.setContent("This is the content of the second news");
        news.setDate(new Date());
        publisher.publish(news);

        System.out.print("Main: End\n");

    }

}
