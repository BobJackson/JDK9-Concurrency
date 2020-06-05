package com.wangyousong.concurrency.ch8.recipe11;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Spliterator -> This interface defines methods that can
 * be used to process and partition a source of elements to be used, for example, the source of
 * a Stream object. You will rarely need to use a Spliterator object directly. Only if you
 * want a different behavior--that is, if you want to implement your own data structure and
 * create Stream from it--use a Spliterator object
 */
public class MySpliterator implements Spliterator<Item> {

    private final Item[][] items;
    private final int start;
    private int end, current;

    public MySpliterator(Item[][] items, int start, int end) {
        this.items = items;
        this.start = start;
        this.end = end;
        this.current = start;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED;
    }

    @Override
    public long estimateSize() {
        return end - current;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Item> consumer) {
        System.out.printf("MySpliterator.tryAdvance.start: %d, %d, %d\n", start, end, current);
        if (current < end) {
            for (int i = 0; i < items[current].length; i++) {
                consumer.accept(items[current][i]);
            }
            current++;
            System.out.print("MySpliterator.tryAdvance.end:true\n");
            return true;
        }
        System.out.print("MySpliterator.tryAdvance.end:false\n");
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super Item> consumer) {
        System.out.print("MySpliterator.forEachRemaining.start\n");
        boolean ret;
        do {
            ret = tryAdvance(consumer);
        } while (ret);
        System.out.print("MySpliterator.forEachRemaining.end\n");
    }


    @Override
    public Spliterator<Item> trySplit() {
        System.out.print("MySpliterator.trySplit.start\n");

        if (end - start <= 2) {
            System.out.print("MySpliterator.trySplit.end\n");
            return null;
        }
        int mid = start + ((end - start) / 2);
        int newEnd = end;
        end = mid;
        System.out.printf("MySpliterator.trySplit.end: %d, %d, %d, %d, %d, %d\n", start, mid, end, mid, newEnd, current);

        return new MySpliterator(items, mid, newEnd);
    }

}
