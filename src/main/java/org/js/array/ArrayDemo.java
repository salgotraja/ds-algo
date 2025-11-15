package org.js.array;

import java.util.Arrays;

public class ArrayDemo {
    public static void main(String[] args) {
        int [] arr = {1, 14, 6, 81, 9};
        System.out.println(arr);

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        for(int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();

        Arrays.stream(arr).forEach(System.out::println);

        int [] arr2 = new int[10];
        Arrays.fill(arr2, 12);
        arr2[0] = 8;
        System.out.println(Arrays.toString(arr2));
    }
}
