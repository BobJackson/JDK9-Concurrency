package com.wangyousong.concurrency.ch6.recipe08;

import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Person> persons = PersonGenerator.generatePersonList(10);

        System.out.print("********************************************************\n");
        int maxSalary = persons.parallelStream()
                .map(Person::getSalary)
                .max(Integer::compare)
                .orElse(0);
        int minSalary = persons.parallelStream()
                .mapToInt(Person::getSalary)
                .min()
                .orElse(0);
        System.out.printf("Salaries are between %d and %d\n", minSalary, maxSalary);
        System.out.print("********************************************************\n");

        boolean condition;

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .allMatch(p -> p.getSalary() > 0);
        System.out.printf("Salary > 0: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .allMatch(p -> p.getSalary() > 10000);
        System.out.printf("Salary > 10000: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .allMatch(p -> p.getSalary() > 30000);
        System.out.printf("Salary > 30000: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .anyMatch(p -> p.getSalary() > 50000);
        System.out.printf("Any with salary > 50000: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .anyMatch(p -> p.getSalary() > 100000);
        System.out.printf("Any with salary > 100000: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        System.out.print("********************************************************\n");
        condition = persons.parallelStream()
                .noneMatch(p -> p.getSalary() > 100000);
        System.out.printf("None with salary > 100000: %b\n", condition);
        System.out.print("********************************************************\n");
        System.out.print("\n");

        Person person;
        System.out.print("********************************************************\n");

        person = persons.parallelStream()
                .findAny()
                .orElse(new Person());
        System.out.printf("Any: %s %s: %d\n", person.getFirstName(), person.getLastName(), person.getSalary());

        person = persons.parallelStream()
                .findFirst()
                .orElse(new Person());
        System.out.printf("First: %s %s: %d\n", person.getFirstName(), person.getLastName(), person.getSalary());

        person = persons.parallelStream()
                .min(Comparator.comparingInt(Person::getSalary))
                .orElse(new Person());
        System.out.printf("First Sorted: %s %s: %d\n", person.getFirstName(), person.getLastName(), person.getSalary());
        System.out.print("********************************************************\n");
        System.out.print("\n");
    }

}
