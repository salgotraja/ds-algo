package org.js.playground;

import java.util.*;
import java.util.stream.Stream;

public class Demonstrator {
    static void main() {
        String str = "Hello World!";

        // Reverse string
        String reversed = new StringBuilder(str).reverse().toString();
        System.out.println("Original String: " + str);
        System.out.println("Reversed String: " + reversed);

        // Check if a string is palindrome
        str = "racecar";
        boolean isPalindrome = str.equalsIgnoreCase(new StringBuilder(str).reverse().toString());
        System.out.println("Is palindrome: " + isPalindrome);

        // Find the Maximum Number in a List
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int max = list.stream().max(Integer::compareTo).orElseThrow();
        System.out.println("Max integer: " + max);

        // Get the First Non-Empty Optional Value
        Optional<String> opt1 = Optional.empty();
        Optional<String> opt2 = Optional.of("Hello");
        Optional<String> opt3 = Optional.of("World");
        String result = Stream.of(opt1, opt2, opt3)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse("default");
        System.out.println("Result: " + result);

        // Convert a List of Strings to Uppercase
        var strList = List.of("Hello", "Java", "World");
        List<String> upper = strList.stream().map(String::toUpperCase).toList();
        upper.forEach(System.out::println);

        // Count Occurrences of a Word in a Sentence
        String sentence = "Java is great, I love java and JAVA programming.";
        long count = Arrays.stream(sentence.split("\\s+"))
                .filter(w -> w.equalsIgnoreCase("java"))
                .count();
        System.out.println("Count: " + count);

        // Generate a Random 6-Digit OTP
        int otp = 100000 + new Random().nextInt(900000);
        System.out.println("OTP: " + otp);

        // Swap Two Variables Without a Temporary Variable
        int a = 3, b = 5;
        System.out.println("a: " + a + " b: " + b);
        a = a + b - (b = a);
        System.out.println("a: " + a + " b: " + b);

        // Remove Duplicates from a List
        list = List.of(1, 2, 3, 2, 5, 4, 5, 6);
        var unique = list.stream().distinct().toList();
        //unique.forEach(System.out::println);
        unique.forEach(i -> System.out.print(i + " "));
        System.out.println();

        // Sort a List in Descending Order
        var immutableList = new ArrayList<>(List.of("banana", "apple", "cherry", "date"));
        immutableList.sort(Comparator.reverseOrder());
        //immutableList.forEach(System.out::println);
        immutableList.forEach(x -> System.out.println(x + " "));
        System.out.println();

        // Better implementation
        var fruits = List.of("banana", "apple", "cherry", "date");
        fruits.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(fruit -> System.out.print(fruit + " "));
        System.out.println();

        // Check If a List Is Empty or Null in One Line
        var emptyList = List.of();
        boolean isEmpty = emptyList == null || emptyList.isEmpty();
        System.out.println("Is empty: " + isEmpty);

        // Merge Two Lists
        var list1 = List.of(1, 2, 3, 4, 5);
        var list2 = List.of(5, 6, 7, 8, 9, 10);
        var merged = Stream.concat(list1.stream(), list2.stream()).toList();
        merged.forEach(x -> System.out.print(x + " "));
        System.out.println();

        // Measure Execution Time of a Block
        long time = System.currentTimeMillis();
        runSomeCode();
        System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms");
    }

    private static void runSomeCode() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.getCause();
        }
    }
}
