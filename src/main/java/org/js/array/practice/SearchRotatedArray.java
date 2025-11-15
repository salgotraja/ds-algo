package org.js.array.practice;

/**
 * Search in Rotated Sorted Array
 * <p>
 * Problem:
 * Find the index of a target element in a sorted array that has been rotated around a pivot point.
 * <p>
 * Input:
 * - nums: int[] - A rotated sorted array, e.g., [4,5,6,7,0,1,2]
 * - target: int - Element to search for, e.g., target = 0
 * <p>
 * Output:
 * - Returns index of target if found, e.g., 4 for target=0
 * - Returns -1 if target is not present
 * <p>
 * Edge Cases:
 * - Empty array
 * - Null array
 * - No rotation (original sorted array)
 * - Full rotation (rotated n times)
 * - Single element array
 * - Target not present in array
 * - Array contains duplicates
 */
public class SearchRotatedArray {

    public static void main(String[] args) {
        SearchRotatedArray search = new SearchRotatedArray();
        int[] arr = new int[] {4, 5, 6, 7, 0, 1, 2};
        System.out.println("0 found at : " + search.findIndex(arr, 0));
    }

    // Binary search
    public int findIndex(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length == 0) {
            return -1;
        }

        int low = 0; int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            // Left half is sorted
            if (arr[low] <= arr[mid]) {
                if (target >= arr[low] && target <= arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            else { // Right half is sorted
                if (target >= arr[mid] && target <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    // Binary search with duplicateHandling
    public int findIndexWithDuplicates(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length == 0) {
            return -1;
        }

        int low = 0; int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;
            }

            // Handle duplicates: skip if boundaries equal mid
            if (arr[low] == arr[mid] && arr[mid] == arr[high]) {
                low++;
                high--;
                continue;
            }

            // Left half is sorted
            if (arr[low] <= arr[mid]) {
                if (target >= arr[low] && target <= arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            else { // Right half is sorted
                if (target >= arr[mid] && target <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

}
