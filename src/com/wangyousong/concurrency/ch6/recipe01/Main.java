package com.wangyousong.concurrency.ch6.recipe01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        System.out.print("Main: Generating parallel streams from different sources\n");
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Creating a stream from a Collection
        System.out.print("********************************************************\n");
        System.out.print("From a Collection:\n");
        List<Person> persons = PersonGenerator.generatePersonList(10000);
        Stream<Person> personStream = persons.parallelStream();
        System.out.printf("Number of persons: %d\n", personStream.count());
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Using a generator
        System.out.print("********************************************************\n");
        System.out.print("From a Supplier:\n");
        Supplier<String> supplier = new MySupplier();
        Stream<String> generatorStream = Stream.generate(supplier);
        generatorStream.parallel().limit(10).forEach(s -> System.out.printf("%s\n", s));
        System.out.println("sequential:");// order
//        generatorStream.limit(10).forEach(s -> System.out.printf("%s\n", s));
//        // Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        Stream.generate(supplier).limit(10).forEach(s -> System.out.printf("%s\n", s));
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // From predefined elements
        System.out.print("********************************************************\n");
        System.out.print("From a predefined set of elements:\n");
        Stream<String> elementsStream = Stream.of("Peter", "John", "Mary");
        elementsStream.parallel().forEach(element -> System.out.printf("%s\n", element));
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // From a File
        System.out.print("********************************************************\n");
        System.out.print("From a File:\n");
        try (BufferedReader br = new BufferedReader(new FileReader("data\\nursery.data"))) {
            Stream<String> fileLines = br.lines();
            System.out.printf("Number of lines in the file: %d\n", fileLines.parallel().count());
            System.out.print("********************************************************\n");
            System.out.print("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // From a directory
        System.out.print("********************************************************\n");
        System.out.print("From a Directory:\n");
        try {
            Stream<Path> directoryContent = Files.list(Paths.get(System.getProperty("user.home")));
            System.out.printf("Number of elements (files and folders):%d\n", directoryContent.parallel().count());
            directoryContent.close();
            System.out.print("********************************************************\n");
            System.out.print("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // From an array
        System.out.print("********************************************************\n");
        System.out.print("From an Array:\n");
        String[] array = {"1", "2", "3", "4", "5"};
        Stream<String> streamFromArray = Arrays.stream(array);
        streamFromArray.parallel().forEach(s -> System.out.printf("%s : ", s));
        System.out.print("\n");
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Random numbers
        System.out.print("********************************************************\n");
        System.out.print("Random number generators:\n");
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(10);
        System.out.println(Double.MAX_VALUE);
        System.out.println(Double.MIN_VALUE);
        System.out.println("=========");
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        // peek() This method is usually used as a debugging tool
        OptionalDouble doubleStreamAverageOptionAl = doubleStream.parallel().peek(d -> System.out.printf("%f : ", d)).average();
        System.out.printf("\nDouble Stream Average: %f\n", doubleStreamAverageOptionAl.orElseThrow());
        System.out.print("********************************************************\n");
        System.out.print("\n");

        // Concatenating streams
        System.out.print("********************************************************\n");
        System.out.print("Concatenating streams:\n");
        Stream<String> stream1 = Stream.of("1", "2", "3", "4");
        Stream<String> stream2 = Stream.of("5", "6", "7", "8");
        Stream<String> finalStream = Stream.concat(stream1, stream2);
        finalStream.parallel().forEach(s -> System.out.printf("%s : ", s));
        System.out.print("\n");
        System.out.print("********************************************************\n");
        System.out.print("\n");

    }

}
