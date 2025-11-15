package org.js.array.practice;

import java.util.NoSuchElementException;

/**
 * Find the Minimum Element
 * - Input: [4, 2, 9, 1]
 * - Output: 1
 * - Boundaries: handle negatives ([-5, -1, -2, -6] -> -6), single element ([42] -> 42)
 * - Throws IllegalArgumentException if an array is null or empty
 * - Returns the minimum element in the given integer array
 */
public class MinElement{
    public static void main(String[] args) {
        MinElement minElement = new MinElement();
        int [] arr = {4, 2, 9, 1};
        System.out.println(minElement.findMin(arr));

        int [] negative_arr = {-5, -1, -2, -6};
        System.out.println(minElement.findMin(negative_arr));

    }
    public int findMin(int [] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty or null");
        }

        int min = arr[0];

        for (int j : arr) {
            if (j < min) {
                min = j;
            }
        }
        return min;
    }

    // @TODO Recursive version of findMin
    // @TODO Compare findMin on sorted vs unsorted arrays and reason about complexity.
    public int findMinRecursive(int [] arr) {
        return 0;
    }

}
