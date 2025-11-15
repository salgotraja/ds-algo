package org.js.array.practice;

import java.util.Arrays;

/**
 * Reverses an array in place or by creating a new array.
 * Input: [1, 2, 3, 4]
 * Output: [4, 3, 2, 1]
 * Boundaries:
 * - Empty array → []
 * - Single element unchanged → [42] → [42]
 * - Null array → IllegalArgumentException
 * Provides two approaches:
 * - reverseBruteForce: Creates new array O(n) space
 * - reverseInPlace: Modifies existing array O(1) space
 */
public class ReverseArray {
    public static void main(String[] args) {
        ReverseArray reverseArray = new ReverseArray();
        int [] arr = {1, 2, 3, 4};
        System.out.println(Arrays.toString(reverseArray.reverseBruteForce(arr)));

        int[] arr2 = {};
        reverseArray.reverseInPlace(arr2);
        System.out.println(Arrays.toString(arr2)); // []

        int[] arr3 = {42};
        reverseArray.reverseInPlace(arr3);
        System.out.println(Arrays.toString(arr3));
    }

    public int [] reverseBruteForce(int [] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[arr.length - 1 - i];
        }
        return temp;
    }

    public void reverseInPlace(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }

        int left = 0;
        int right = arr.length - 1;

        while ((left < right)) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }

    public void reverse(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }
}
