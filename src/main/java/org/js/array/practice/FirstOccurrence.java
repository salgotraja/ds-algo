package org.js.array.practice;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * First Occurrence in Sorted Array
 * <p>
 * Problem Statement:
 * Find the index of the first occurrence of a target element in a sorted array.
 * <p>
 * Input:
 * - nums: int[] - A sorted array in ascending order, e.g. [1,2,2,2,3,4]
 * - target: int - Element to find, e.g., target=2
 * <p>
 * Expected Output:
 * - Returns index of the first occurrence if found, e.g., 1
 * - Returns -1 if the target is not present
 * <p>
 * Edge Cases:
 * - Empty array
 * - Null array
 * - Single element array
 * - Target not present in array
 * - All elements are the same as target, e.g. [2,2,2]
 * - Target at start/end of an array
 * <p>
 * Boundaries:
 * - Array length: 0 to Integer.MAX_VALUE
 * - Array values: Integer.MIN_VALUE to Integer.MAX_VALUE
 * - Array must be sorted in ascending order
 * <p>
 * Examples:
 * Input: [1,2,2,2,3,4], target=2 → Output: 1
 * Input: [2,2,2], target=2 → Output: 0
 * Input: [1,3,5], target=2 → Output: -1
 * Input: [], target=2 → Output: -1
 */
public class FirstOccurrence {
    public static void main(String[] args) {
        FirstOccurrence firstOccurrence = new FirstOccurrence();
        int[] arr = new int[] {1,2,2,2,3,4};
        System.out.println("First occurrence of 2 is: " + firstOccurrence.findFirstOccurrence(arr, 2));
        System.out.println("Last occurrence of 2 is: " + firstOccurrence.findLastOccurrence(arr, 2));
    }

    public int findFirstOccurrence(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                result = mid;
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    public int findLastOccurrence(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                result = mid;
                low = mid + 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    public int[] findRange(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length == 0) {
            return new int[]{-1, -1};
        }

        int first = findFirstOccurrence(arr, target);
        int last = findLastOccurrence(arr, target);

        return new int[]{first, last};
    }

    public int[] findRangeOptimized(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length == 0) {
            return new int[]{-1, -1};
        }

        int low = 0, high = arr.length - 1;
        int first = -1, last = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                first = mid;
                last = mid;

                // expand left
                int left = mid - 1;
                while (left >= 0 && arr[left] == target) {
                    first = left;
                    left--;
                }

                // expand right
                int right = mid + 1;
                while (right < arr.length && arr[right] == target) {
                    last = right;
                    right++;
                }

                return new int[]{first, last};
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new int[]{-1, -1}; // not found
    }


    // Optimized Pure O(log n) Implementation
    public int[] findRangeLogN(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length == 0) {
            return new int[]{-1, -1};
        }

        int first = findFirstOccurrence(arr, target);
        int last = findLastOccurrence(arr, target);

        return new int[]{first, last};
    }

}
