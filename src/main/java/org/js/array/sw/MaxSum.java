package org.js.array.sw;

public class MaxSum {
    public static void main(String[] args) {
        MaxSum max = new MaxSum();
        int[] arr= new int[]{1,7,6,2,3,4,5};
        int k1 = 3;
        System.out.println("Max sum for arr2 with k=" + k1 + ": " + max.maxSum(arr, k1));
        int[] arr2 = {-1, -2, -3, -4, -5};
        int k2 = 2;
        System.out.println("Max sum for arr2 with k=" + k2 + ": " + max.maxSum(arr2, k2)); // Expected: -3 (-1 + -2)

        int[] arr3 = {5, 2, -1, 0, 3};
        int k3 = 3;
        System.out.println("Max sum for arr3 with k=" + k3 + ": " + max.maxSum(arr3, k3)); // Expected: 6 (5+2-1 or 0+3+?)
    }

    /**
     * Computes the maximum sum of any contiguous subarray of length k.
     * @param arr The input array (non-null, non-empty).
     * @param k The subarray length (positive integer).
     * @return The maximum sum, or -1 if arr.length < k.
     * @throws IllegalArgumentException if arr is null/empty or k <= 0.
     */
    public int maxSum(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        if (arr.length < k) {
            return -1;
        }

        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += arr[i];
        }

        int maxSum = sum;

        for (int i = k; i < arr.length; i++) {
            sum = sum + arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    /**
     * Computes the maximum sum of any contiguous subarray of length k, using long for sums to avoid overflow.
     * @param arr The input array (non-null, non-empty).
     * @param k The subarray length (positive integer).
     * @return The maximum sum, or -1 if arr.length < k.
     * @throws IllegalArgumentException if arr is null/empty, k <= 0, or if the sum overflows int range.
     */
    public int maxSumLong(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        if (arr.length < k) {
            return -1;
        }
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += arr[i];
        }
        long maxSum = sum;

        for (int i = k; i < arr.length; i++) {
            sum = sum + arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, sum);
        }

        if (maxSum > Integer.MAX_VALUE || maxSum < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Sum overflows int range");
        }
        return (int) maxSum;
    }
}
