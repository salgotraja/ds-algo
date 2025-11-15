package org.js.array.practice;

/**
 * This class provides a method to find the minimum element in a rotated sorted array.
 *
 * Problem Statement:
 * A rotated sorted array is an array that has been shifted at some pivot index,
 * potentially dividing a sorted array into two subarrays. This class implements
 * an efficient algorithm to determine the smallest element in such an array.
 *
 * Requirements:
 * - The input array must have been initially sorted in ascending order before rotation.
 * - Input can include duplicate values.
 * - Runtime complexity: O(log n) in the average case, assuming binary search principles.
 *
 * Input:
 * - An integer array representing the rotated sorted array.
 *
 * Output:
 * - Returns the minimum element in the array.
 *
 * Edge Cases:
 * - Empty array.
 * - Array with a single element.
 * - Array is not rotated (already sorted in ascending order).
 * - Array with all identical elements.
 *
 * Boundaries:
 * - Array length: 0 to Integer.MAX_VALUE.
 * - Array values: Integer.MIN_VALUE to Integer.MAX_VALUE.
 */
public class FindMinRotatedArray {
    public static void main(String[] args) {
        FindMinRotatedArray search = new FindMinRotatedArray();
        /*int[] arr = new int[]{4,5,6,7,0,1,2};
        System.out.println(search.findMin(arr));*/

        int[] arr1 = new int[] {3, 4, 5, 1, 2};
        //System.out.println(search.findMin(arr1));

        int [] arr2 = new int[] {2, 2, 2, 0, 1};
        System.out.println(search.findMinWithDuplicates(arr2));
    }

    // without duplicates
    public int findMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        int low = 0; int high = arr.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] > arr[high]){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return arr[low];
    }

    // With duplicates
    public int findMinWithDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        int low = 0; int high = arr.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] > arr[high]) {
                low = mid + 1;
            } else if (arr[mid] < arr[high]) {
                high = mid;
            } else { // arr[mid] == arr[high]
                high-- ; // shrink search space
            }
        }

        return arr[low];
    }
}
