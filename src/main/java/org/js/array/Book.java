package org.js.array;

public class Book {
    int isbn;
    String name;
    int price;

    public Book(String name, int isbn, int price) {
        this.name = name;
        this.isbn = isbn;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
