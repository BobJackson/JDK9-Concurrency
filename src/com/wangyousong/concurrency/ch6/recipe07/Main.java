package com.wangyousong.concurrency.ch6.recipe07;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * When you use the unordered() method, you're not executing any code that internally
 * changes the order of the elements in the data structure. You're only deleting a condition that
 * would be taken into account for some methods otherwise.
 * List<Person> personSet = new ArrayList<>(persons);
 * The main purpose of the unordered() method is to delete a
 * constraint that limits the performance of parallel streams.
 */
public class Main {

    public static void main(String[] args) {

        // Sorted array of integer
        int[] numbers = {9, 8, 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print("********************************************************\n");
        Arrays.stream(numbers)
                .parallel()
                .sorted()
                .forEachOrdered(n -> System.out.printf("%d\n", n));
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Sorted for Persons
        System.out.print("********************************************************\n");
        List<Person> persons = PersonGenerator.generatePersonList(10);
        persons.parallelStream()
                .sorted()
                .forEachOrdered(p -> System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName()));
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Unordered
        System.out.print("********************************************************\n");
        Person person = persons.get(0);
        System.out.printf("%s %s\n", person.getFirstName(), person.getLastName());

        TreeSet<Person> personSet = new TreeSet<>(persons);

        for (int i = 0; i < 10; i++) {
            System.out.printf("********** %d ***********\n", i);
            person = personSet.stream()
                    .parallel()
                    .limit(1)
                    .collect(Collectors.toList())
                    .get(0);
            System.out.printf("%s %s\n", person.getFirstName(), person.getLastName());

            person = personSet.stream()
                    .unordered()
                    .parallel()
                    .limit(1)
                    .collect(Collectors.toList())
                    .get(0);
            System.out.printf("%s %s\n", person.getFirstName(), person.getLastName());
            System.out.printf("************ %d *************\n", i);
        }

    }

}
