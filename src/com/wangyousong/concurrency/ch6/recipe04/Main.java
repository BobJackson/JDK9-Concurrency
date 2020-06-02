package com.wangyousong.concurrency.ch6.recipe04;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Person> persons = PersonGenerator.generatePersonList(10);

        // For each parallel
        System.out.print("********************************************************\n");
        System.out.print("*Parallel forEach()\n");
        persons.parallelStream().forEach(p -> System.out.printf("*%s, %s\n", p.getLastName(), p.getFirstName()));
        System.out.print("********************************************************\n");
        System.out.print("*\n");

        // For each ordered
        List<Double> doubles = DoubleGenerator.generateDoubleList(10, 100);

        // For each ordered parallel with numbers
        System.out.print("********************************************************\n");
        System.out.print("*Parallel forEachOrdered() with numbers\n");
        doubles.parallelStream().sorted().forEachOrdered(n -> System.out.printf("*%f\n", n));
        System.out.print("********************************************************\n");
        System.out.print("*\n");

        // For each after sort with numbers
        System.out.print("********************************************************\n");
        System.out.print("*Parallel forEach() after sorted() with numbers\n");
        doubles.parallelStream().sorted().forEach(n -> System.out.printf("*%f\n", n));
        System.out.print("********************************************************\n");
        System.out.print("*\n");

        // For each ordered parallel with objects
        System.out.print("********************************************************\n");
        System.out.print("*Parallel forEachOrdered with Persons\n");
        persons.parallelStream().sorted().forEachOrdered(p -> System.out.printf("*%s, %s\n", p.getLastName(), p.getFirstName()));
        System.out.print("********************************************************\n");
        System.out.print("*\n");

        // Peek
        System.out.print("********************************************************\n");
        System.out.print("*Peek\n");
        doubles
                .parallelStream()
                .peek(d -> System.out.printf("*Step 1: Number: %f\n", d))
                .filter(n -> n < 50)
                .peek(d -> System.out.printf("*Step 2: Number: %f\n", d))
                .forEach(d -> System.out.printf("*Final Step: Number: %f\n", d));
        System.out.print("********************************************************\n");


    }

}
