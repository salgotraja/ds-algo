package org.js.array;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayOfObjects {
    public static void main(String[] args) {
        Book [] books = new Book[3];
        Book b = new Book("Java", 1, 1000);
        books[1]  = new Book("C++", 2, 1200);
        books[2] = new Book("Python", 3, 100);
        books[0] = b;

        System.out.println(books);
        System.out.println(Arrays.toString(books));

        //Arrays.sort(books, (b1, b2) -> (b1.price - b2.price));
        Arrays.sort(books, Comparator.comparingInt(b2 -> b2.price));
        System.out.println(Arrays.toString(books));
    }
}
