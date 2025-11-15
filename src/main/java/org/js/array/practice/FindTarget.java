package org.js.array.practice;

import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Binary Search Implementation
 * <p>
 * Problem Statement:
 * Implement binary search to find the index of a target element in a sorted array.
 * <p>
 * Requirements:
 * - Array must be sorted in ascending order
 * - Search should be performed in O(log n) time complexity
 * - Should handle arrays with duplicate values
 * <p>
 * Input:
 * - nums: int[] - A sorted array of integers, e.g., nums=[1,2,3,4,5,6]
 * - target: int - The value to search for, e.g., target=4
 * <p>
 * Expected Output:
 * - Returns index of target if found, e.g., 3
 * - Returns -1 if target is not present
 * <p>
 * Edge Cases:
 * - Empty array
 * - Null array
 * - Single element array
 * - Target value not present in array
 * - Target value occurs multiple times
 * <p>
 * Boundaries:
 * - Array length: 0 to Integer.MAX_VALUE
 * - Array values: Integer.MIN_VALUE to Integer.MAX_VALUE
 * - Returns any valid index for duplicate values
 */
public class FindTarget {
    public static void main(String[] args) {
        FindTarget findTarget = new FindTarget();
        int[] arr = new int[] {1, 2, 3, 4, 5, 6};
        System.out.println(findTarget.binarySearch(arr, 4));

        int[] arr1 = new int[] {3, 1 , 5, 9};
        findTarget.sort(arr1);
        System.out.println(Arrays.toString(arr1));
    }

    public int binarySearch(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length == 0) {
            return -1;
        }

        // Optional check, else assume input is sorted. If we check, it makes binary search O(n + log n)
        if (!isSorted(arr)) {
            throw new InputMismatchException("Array must be sorted");
        }

        int beg = 0;
        int end = arr.length -1;

        while (beg <= end) {
            int mid = beg + (end - beg) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                beg = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    // O(n) complexity
    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    private boolean sorted(int[] arr, boolean ascending) {
        for (int i = 0; i < arr.length; i++) {
            if (ascending && arr[i] > arr[i + 1]) return false;
            if (!ascending && arr[i] < arr[i + 1]) return false;
        }

        return true;
    }

    // O(n²) complexity
    private void sort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
           for (int j = 0; j < arr.length; j++) {
               if (arr[i] < arr[j]) {
                   int temp = arr[i];
                   arr[i] = arr[j];
                   arr[j] = temp;
               }
           }
        }
    }

    // O(n²) complexity
    private void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

}
