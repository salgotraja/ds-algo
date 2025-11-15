package org.js.array;

import java.util.Arrays;
import java.util.Comparator;

public class TwoDimensionalArrays {

    public static void traverse(int[][] arr) {
        System.out.println(arr.length);

        int rows = arr.length;
        int cols = arr[0].length;



        for (int[] ints : arr) {
            for (int j = 0; j < cols; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static int getSum(int [] arr) {
        int sum = 0;
        for (int val : arr) {
            sum = sum + val;
        }

        return sum;
    }

    public static void main(String[] args) {
        int [][] arr = {{1, 2, 3, 400}, {4, 5, 6, 100}, {7, 8, 9, 10}};

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.deepToString(arr));
        traverse(arr);

        Arrays.sort(arr, Comparator.comparingInt(TwoDimensionalArrays::getSum));
        //Arrays.sort(arr, (a, b) -> getSum(a) - getSum(b));
        System.out.println(Arrays.deepToString(arr));

        // Comparing last element
        Arrays.sort(arr, Comparator.comparingInt(o -> o[o.length - 1]));
        //Arrays.sort(arr, (o1, o2) -> o1[o1.length -1] - o2[o2.length-1]);

        System.out.println(Arrays.deepToString(arr));

        // Comparing first element
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
        System.out.println(Arrays.deepToString(arr));
    }
}
