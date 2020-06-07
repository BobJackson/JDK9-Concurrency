package com.wangyousong.concurrency.ch11.recipe12;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
/*
    The biggest advantage we obtained using streams is the simplicity of the solution and its
    development time. With only one line of code, we implemented the solution.

    With Streams, you can divide your algorithm into simple steps that can be expressed in an
    elegant way, be easy to program and understand.
 */
public class Main {

    public static void main(String[] args) {
        List<Person> persons = PersonGenerator.generatePersonList(100_000);

        Date start, end;

        start = new Date();
        Map<String, List<Person>> personsByName = persons.parallelStream()
                .collect(Collectors.groupingByConcurrent(Person::getFirstName));
        end = new Date();

        // 1000_000 -> Collect: 10 - 73
        // 100_000 -> Collect: 10 - 26
        System.out.printf("Collect: %d - %d\n", personsByName.size(), end.getTime() - start.getTime());

        start = new Date();
        ConcurrentHashMap<String, ConcurrentLinkedDeque<Person>> forkJoinMap = new ConcurrentHashMap<>();
        PersonMapTask personMapTask = new PersonMapTask(persons, forkJoinMap);
        ForkJoinPool.commonPool().invoke(personMapTask);
        end = new Date();

        // 1000_000 -> Collect ForkJoinPool: 10 - 162 -> it's related to (end - star > separation threshold)
        // 100_000 -> Collect ForkJoinPool: 10 - 29
        System.out.printf("Collect ForkJoinPool: %d - %d\n", forkJoinMap.size(), end.getTime() - start.getTime());
    }

}
