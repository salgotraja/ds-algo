package org.js.array.practice;

import java.util.Arrays;

/**
 * Prefix Sum Array
 * - Problem: Build a prefix sum array where each element represents the sum of all previous elements including itself.
 * - Input: [1,2,3,4]
 * - Output: [1,3,6,10] (1, 1+2, 1+2+3, 1+2+3+4)
 * - Edge cases: Empty array → return empty array [], null → throw IllegalArgumentException
 * - Boundary: Large integers → watch for integer overflow, consider using long if needed
 * - Time Complexity: O(n) as it requires one pass through array
 * - Space Complexity: O(n) for storing result array
 */
public class PrefixSumArray {

    public static void main(String[] args) {
        PrefixSumArray sumArray = new PrefixSumArray();
        int[] arr = new int[] {1, 2, 3, 4};
        sumArray.prefixSum(arr);
        System.out.println(Arrays.toString(arr));

        int[] arr1 = new int[] {2, -3, 1, -5};
        sumArray.prefixSum_(arr1);
        System.out.println(Arrays.toString(arr1));

        int[] arr2 = new int[] { -1, -2, -3, 5};
        System.out.println(Arrays.toString(sumArray.prefixSumNonMutating(arr2)));
    }

    public void prefixSum(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }

        int running = 0;

        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];

            if (running > 0 && val > Integer.MAX_VALUE - running) {
                throw new ArithmeticException("Integer overflow");
            }

            if (running < 0 && val < Integer.MIN_VALUE - running) {
                throw new ArithmeticException("Integer underflow");
            }

            running += val;
            arr[i] = running;
        }
    }

    public void prefixSum_(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length-1; i++) {
            arr[i+1] = arr[i] + arr[i+1];
        }
    }

    /**
     * Non-mutating version: returns a new array with prefix sums.
     */
    public int[] prefixSumNonMutating(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int n = arr.length;
        int[] res = new int[n];
        int running = 0;

        for (int i = 0; i < n; i++) {
            running += arr[i];
            res[i] = running;
        }

        return res;
    }
}
