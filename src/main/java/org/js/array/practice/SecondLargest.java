package org.js.array.practice;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Problem: Find the second-largest element in an array of integers.
 * <p>
 * Input:
 *     - Integer array of size n, where n >= 1
 *     - Example: [10, 20, 5, 8]
 * <p>
 * Output:
 *     - Integer representing the second-largest element
 *     - Example: 10
 * <p>
 * Constraints:
 *     - Array length: 1 <= n <= 10^5
 *     - Array elements: -10^9 <= arr[i] <= 10^9
 * <p>
 * Edge Cases:
 *     - Array with all equal elements ([5, 5, 5]) → return null or throw exception
 *     - Array with a single element → return null or throw exception
 *     - Array with duplicates ([1, 2, 2]) → 1 (second distinct maximum)
 *     - Null array → throw IllegalArgumentException
 */
public class SecondLargest {
    public static void main(String[] args) {
        SecondLargest second = new SecondLargest();
        int [] arr =  new int[]{10, 20, 5, 8};
        System.out.println(second.secondLargestDistinct(arr));
    }

    public Integer secondLargestDistinct(int[] arr) {
        if (arr == null) throw new IllegalArgumentException("Array cannot be null");
        if (arr.length < 2) return null;

        Integer max = null;
        Integer second = null;

        for (int v : arr) {
            if (max == null || v > max) {
                second = max;
                max = v;
            } else if (v != max && (second == null || v > second)) {
                second = v;
            }
        }

        return second;
    }

    public int secondLargestDistinctOrThrow(int[] arr) {
        Integer res = secondLargestDistinct(arr);
        if (res == null) {
            throw new NoSuchElementException("No second distinct largest element");
        }
        return res;
    }

}
