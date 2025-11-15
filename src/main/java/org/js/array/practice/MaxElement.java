package org.js.array.practice;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Find the Maximum Element
 * - Input: [1, 3, 7, 2, 5]
 * - Output: 7
 * - Boundaries: handle negatives ([-5, -2, -9] -> -2), single element ([42] -> 42)
 * - Throws IllegalArgumentException if an array is null or empty
 * - Returns the maximum element in the given integer array
 */
public class MaxElement {

    public static void main(String[] args) {
        MaxElement maxElement = new MaxElement();
        int [] arr = new int[] {1, 3, 7, 2, 5};
        int [] sorted = maxElement.sort(arr);
        System.out.println(Arrays.toString(sorted));
        System.out.println("Max number is: " + sorted[sorted.length-1]);
        int max = maxElement.findMax(arr);
        System.out.println("max: " + max);

        int [] arr1 = new int[] {1, -3, -7, 2, -5, 0};
        System.out.println(maxElement.findMinAndMax(arr1));

        int[] arr2 = {100, 3, 8, -2, 45, 7, 90};
        System.out.println(maxElement.findMinAndMaxOptimized(arr2));
    }

    public int findMax(int [] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty or null");
        }

        int max = arr[0];
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }
    public int [] sort(int [] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty or null");
        }

        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr.length; j++) {
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * Implement findMinAndMax method that returns both values in a single pass (O(n), fewer comparisons).
     * Boundaries: negative values, single element, empty array and null handling
     * @param arr input array
     * @return MinMax object holding min and max
     */
    public MinMax findMinAndMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty or null");
        }

        int min = arr[0];
        int max = arr[0];

        for (int j : arr) {
            if (j > max) {
                max = j;
            } else if (j < min) {
                min = j;
            }
        }
        return new MinMax(min, max);
    }

    public MinMax findMinAndMaxOptimized(int [] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty or null");
        }

        int min, max;
        int i;

        if (arr.length % 2 == 0) {
            // If even, pick first two
            if (arr[0] < arr[1]) {
                min = arr[0];
                max = arr[1];
            } else {
                min = arr[1];
                max = arr[2];
            }
            i = 2; // start with index 2
        } else {
            // If odd, pick first one
            min = max = arr[0];
            i = 1; // start from index 1
        }

        // Process pairs
        while (i < arr.length - 1) {
            if (arr[i] < arr[i+1]) {
                if (arr[i] < min) {
                    min = arr[i];
                }
                if (arr[i+1] > max){
                    max = arr[i+1];
                }
            } else {
                if (arr[i + 1] < min){
                    min = arr[i+1];
                }
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            i += 2;
        }

        return new MinMax(min, max);
    }

    public record MinMax(int min, int max) { }
}
