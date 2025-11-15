package org.js.array.practice;

/**
 * Check if Array is Sorted
 * - Input: [1, 2, 3, 4] → true (ascending)
 * - Input: [5, 3, 1] → true (descending)
 * - Input: [3, 1, 2] → false
 * - Boundaries: empty or single element → true
 * - Supports strict mode (no duplicates allowed) and non-strict mode (duplicates allowed)
 * - Throws IllegalArgumentException if array is null
 * - Returns true if array is sorted in specified order (ascending/descending)
 */
public class SortedArr {
    public static void main(String[] args) {
        SortedArr sortedArr = new SortedArr();

        int[] arrAsc = {1, 2, 3, 4};
        int[] arrNonStrict = {1, 1, 2, 2, 3};
        int[] arrDesc = {5, 3, 1};
        int[] arrUnsorted = {3, 1, 2};

        System.out.println("Ascending strict: " + sortedArr.checkSorted(arrAsc, true, true));   // true
        System.out.println("Ascending non-strict: " + sortedArr.checkSorted(arrNonStrict, true, false)); // true

        System.out.println("Descending strict: " + sortedArr.checkSorted(arrDesc, false, true));   // true
        System.out.println("Descending non-strict: " + sortedArr.checkSorted(arrNonStrict, false, false)); // false

        System.out.println("Unsorted: " + sortedArr.checkSorted(arrUnsorted));
    }

    public boolean checkSorted(int[] arr) {
        return checkSorted(arr, true, true);
    }

    // Overloaded → ascending/descending, strict/non-strict
    public boolean checkSorted(int[] arr, boolean ascending, boolean strict) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length <= 1) {
            return true; // empty or single-element arrays are sorted
        }

        for (int i = 0; i < arr.length - 1; i++) {
            if (ascending) {
                if (strict && arr[i] >= arr[i + 1]) return false;
                if (!strict && arr[i] > arr[i + 1]) return false;
            } else { // descending
                if (strict && arr[i] <= arr[i + 1]) return false;
                if (!strict && arr[i] < arr[i + 1]) return false;
            }
        }
        return true;
    }

    public boolean checkSortedArr(int[] arr) {
        return checkSortedArr(arr, true);
    }

    public boolean checkSorted_(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length <= 1) {
            return true;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSortedArr(int[] arr, boolean ascending) {
        if (arr == null) throw new IllegalArgumentException("Array cannot be null");
        if (arr.length <= 1) return true;

        for (int i=0; i<arr.length-1; i++) {
            if (ascending && arr[i] > arr[i+1]) return false;
            if (!ascending && arr[i] < arr[i+1]) return false;
        }

        return true;
    }
}
