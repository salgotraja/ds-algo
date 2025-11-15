package org.js.array.practice;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Two Sum - Find indices of two numbers that add up to target
 * Input: nums=[2,7,11,15], target=9
 * Output: [0,1] (indices of numbers that sum to target)
 * Note: Return any valid solution if multiple exists
 */
public class TwoSum {

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int[] arr = new int[] {2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSum.findTwoSum(arr, 9)));

        int[] arr1 = new int[] {1, 3, 2, 3, 0, 1, 5, 0};
        System.out.println(Arrays.toString(twoSum.findTwoSumsBrute(arr1, 5)));

        int[] arr2 = new int[] {0, 1, 2, 3, 5, 8};
        System.out.println(Arrays.toString(twoSum.twoSumSorted(arr2, 8)));
    }

    // Time = O(n²), Space = O(1).
    public int[] findTwoSumsBrute(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array can not be null or empty");
        }

        if (arr.length < 2) {
            throw new IllegalArgumentException("Minimum size of array should be two");
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == k) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[] {-1, -1};
    }

    // O(n) time, O(n) space
    public int[] findTwoSum(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array can not be null or empty");
        }

        if (arr.length < 2) {
            throw new IllegalArgumentException("Minimum size of array should be two");
        }

        var map = new HashMap<Integer, Integer>();

        for (int i = 0; i < arr.length; i++) {
            int complement = k - arr[i];

            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(arr[i], i);
        }

        return new int[] {-1, -1};
    }

    // Two pointers for Two Sum, when an array is sorted.
    // Time = O(n), Space = O(1)
    public int [] twoSumSorted(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array can not be null or empty");
        }

        if (arr.length < 2) {
            throw new IllegalArgumentException("Minimum size of array should be two");
        }

        // Assume array is sorted, else sort the array. Skipping this check for now on the basis of the assumption

        int left = 0; int right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == k) {
                return new int[] {left, right};
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }

        return new int[] {-1, -1};
    }
}
