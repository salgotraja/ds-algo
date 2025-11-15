package org.js.linkedlist;

public class LinkedListDemo {
    public static void main(String[] args) throws Exception {
        LinkedList l = new LinkedList();
        l.addLast(40);
        l.addFirst(10);
        l.addFirst(20);
        l.addFirst(30);
        l.addLast(50);
        l.addAt(0, 0);
        l.addAt(100, l.getSize());
        l.display();
        l.addAt(4, 3);
        l.display();
        System.out.println(l.getSize());
        System.out.println("----------------------");
        System.out.println("First: " + l.getFirst());
        System.out.println("Last: " + l.getLast());
        System.out.println("At index: " + l.getAt(4));
        l.addFirst(40);
        l.display();
        l.removeFirst();
        l.display();
        l.removeLast();
        l.display();
        System.out.println(l.getSize());
        System.out.println(l.removeAt(2));
        l.display();
    }
}
