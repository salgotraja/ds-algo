package org.js.array;

public class DynamicArrayPilot {
    public static void main(String[] args) {
        DynamicArray dArr = new DynamicArray(5);

        dArr.add(1);
        dArr.add(2);
        dArr.add(3);
        dArr.add(4);
        dArr.add(5);

        System.out.println("Initial Capacity: " + dArr.getCapacity());
        System.out.println("Initial Size: " + dArr.size());
        dArr.add(6);
        System.out.println("Capacity: " + dArr.getCapacity());
        System.out.println("Size: " + dArr.size());
        System.out.println(dArr.remove());
        System.out.println("Capacity: " + dArr.getCapacity());
        System.out.println("Size: " + dArr.size());
        dArr.add(8);
        dArr.add(9);
        dArr.add(10);
        dArr.add(11);
        dArr.add(12);
        dArr.add(13);
        System.out.println("Capacity: " + dArr.getCapacity());
        System.out.println("Size: " + dArr.size());

        dArr.add(3, 1111);

        System.out.println(dArr);
        System.out.println(dArr.contains(1112));
        System.out.println(dArr.removeAt(3));
        System.out.println(dArr);
    }
}
