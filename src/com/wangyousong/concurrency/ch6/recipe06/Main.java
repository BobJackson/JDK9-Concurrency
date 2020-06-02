package com.wangyousong.concurrency.ch6.recipe06;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // Create list of persons
        List<Person> persons = PersonGenerator.generatePersonList(100);

        // Map to double
        System.out.print("*********************************************************\n");
        persons.parallelStream()
                .mapToDouble(Person::getSalary)
                .distinct()
                .forEach(d -> System.out.printf("*Salary: %f\n", d));

        long size = persons.parallelStream()
                .mapToDouble(Person::getSalary)
                .distinct()
                .count();
        System.out.printf("*Size: %d\n", size);
        System.out.print("*********************************************************\n");
        System.out.print("\n");

        // Map to object
        System.out.print("*********************************************************\n");
        List<BasicPerson> basicPersons = persons.parallelStream().map(p -> {
            BasicPerson bp = new BasicPerson();
            bp.setName(p.getFirstName() + " " + p.getLastName());
            bp.setAge(getAge(p.getBirthDate()));
            return bp;
        }).collect(Collectors.toList());

        basicPersons.forEach(bp -> System.out.printf("*%s: %d\n", bp.getName(), bp.getAge()));
        System.out.print("*********************************************************\n");
        System.out.print("\n");

        // Flap Map
        System.out.print("*********************************************************\n");
        List<String> file = FileGenerator.generateFile(100);
        Map<String, Long> wordCount = file.parallelStream()
                .flatMap(line -> Stream.of(line.split("[ ,.]")))
                .filter(w -> w.length() > 0)
                .sorted()
                .collect(Collectors.groupingByConcurrent(e -> e, Collectors.counting()));

        wordCount.forEach((k, v) -> System.out.printf("%s: %d\n", k, v));
        System.out.print("*********************************************************\n");
    }

    private static long getAge(Date birthDate) {
        LocalDate start = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.YEARS.between(start, LocalDate.now());
    }

}
