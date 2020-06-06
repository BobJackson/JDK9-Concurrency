package com.wangyousong.concurrency.ch9.recipe05;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {

        AtomicLong counter = new AtomicLong(0);
        Random random = new Random();

        /*
        This method doesn't need to process the elements to calculate the
        returned value, so the peek() method will never be executed. You won't see any of the
        messages of the peek method in the console, and the value of the counter variable will be 0.
         */
        long streamCounter = random.doubles(1000).parallel().peek(number -> {
            long actual = counter.incrementAndGet();
            System.out.printf("%d -- %f\n", actual, number);
        }).count();

        System.out.printf("Counter: %d\n", counter.get());
        System.out.printf("Stream Counter: %d\n", streamCounter);

        counter.set(0);
        random.doubles(1000).parallel().peek(number -> {
            long actual = counter.incrementAndGet();
            System.out.printf("Peek: %d - %f\n", actual, number);
        }).forEach(number -> {
            System.out.printf("For Each: %f\n", number);
        });

        /*
        The peek() method is an intermediate operation of a stream. Like with all intermediate
        operations, they are executed lazily, and they only process the necessary elements. This is
        the reason why it's never executed in the first case.
         */
        System.out.printf("Counter: %d\n", counter.get());
    }
}
