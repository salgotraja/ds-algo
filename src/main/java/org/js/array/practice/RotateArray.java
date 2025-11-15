package org.js.array.practice;

import java.util.Arrays;

/**
 * Rotate Array (k steps)
 * - Problem: Rotate the array to the right by k steps.
 * - Input: arr=[1,2,3,4,5,6,7], k=3
 * - Output: [5,6,7,1,2,3,4]
 * - Edge cases: k=0, k=n, k>n, k<0, null array, empty array
 * - Solution approaches:
 * 1. Using extra array: O(n) time, O(n) space
 * 2. In-place reversal: O(n) time, O(1) space
 * - Boundary: Handle modulo k % n.
 */
public class RotateArray {
    public static void main(String[] args) {
        RotateArray rArray = new RotateArray();
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7};

        System.out.println(Arrays.toString(rArray.rotateArray(arr, 3)));

        rArray.rotateInPlace(arr, 3);
        System.out.println(Arrays.toString(arr));
    }

    public int[] rotateArray(int[] arr, int steps) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int n = arr.length;
        if (n <= 1) {
            return arr;
        }

        int k = steps % n;
        if (k < 0) k+= n;
        if (k == 0)  return arr;

        int [] res = new int[n];
        int idx = 0;

        for (int i = n - k; i < n; i++) {
            res[idx++] = arr[i];
        }

        for (int i = 0; i < n - k; i++) {
            res[idx++] = arr[i];
        }

        return res;
    }

    public int[] rotateWithExtraArray(int[] arr, int steps) {
        if (arr == null) throw new IllegalArgumentException("Array cannot be null");
        int n = arr.length;
        if (n <= 1) return arr;

        int k = steps % n;
        if (k < 0) k += n;
        if (k == 0) return arr;

        int[] res = new int[n];
        int idx = 0;

        for (int i = n - k; i < n; i++) res[idx++] = arr[i];
        for (int i = 0; i < n - k; i++) res[idx++] = arr[i];

        return res;
    }

    /**
     * In-place rotation using the reversal algorithm.
     * Time: O(n), Space: O(1)
     */
    public void rotateInPlace(int[] arr, int steps) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int n = arr.length;
        if (n <= 1) return;

        int k = steps % n;
        if (k < 0) k += n;
        if (k == 0) return;

        reverse(arr, 0, n - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
    }

    public int[] rotateInPlaceArr(int[] arr, int steps) {
        if (arr == null) throw new IllegalArgumentException("Array cannot be null");
        int n = arr.length;
        if (n <= 1) return arr;

        int k = steps % n;
        if (k < 0) k += n;
        if (k == 0) return arr;

        reverse(arr, 0, n - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);

        return arr;
    }

    private void reverse(int[] arr, int l, int r) {
        while (l < r) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            l++;
            r--;
        }
    }

    /**
     * Cyclic rotation algorithm that rotates elements in-place by moving each element to its final position.
     * Time: O(n) since each element is moved exactly once.
     * Space: O(1) since it only uses a few temporary variables.
     *
     * @param arr   Array to rotate
     * @param steps Number of positions to rotate right
     * @return Rotated array
     */
    public int [] rotateCyclic(int[] arr, int steps) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int n = arr.length;
        if (n <= 1) return arr;

        int k = steps % n;
        if (k < 0) k += n;
        if (k == 0) return arr;

        int count = 0;

        for (int start = 0; count < n; start++) {
            int current = start;
            int prevValue = arr[start];

            do {
                int next = (current + k) % n;
                int temp = arr[next];
                arr[next] = prevValue;
                prevValue = temp;
                current = next;
                count++;
            } while (start != current);
        }

        return arr;
    }
}
