package org.js.array.sw;

public class MinSum {

    public static void main(String[] args) {
        MinSum min = new MinSum();
        int[] arr= new int[]{1,7,6,2,3,4,5};
        int k1 = 3;
        System.out.println("Min sum for arr2 with k=" + k1 + ": " + min.findMinSum(arr, k1));
        int[] arr2 = {-1, -2, -3, -4, -5};
        int k2 = 2;
        System.out.println("Min sum for arr2 with k=" + k2 + ": " + min.findMinSum(arr2, k2));

        int[] arr3 = {5, 2, -1, 0, 3};
        int k3 = 3;
        System.out.println("Min sum for arr3 with k=" + k3 + ": " + min.findMinSum(arr3, k3));
    }

    public int findMinSum(int[] arr, int k) {
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
        int minSum = sum;

        for (int i = k; i < arr.length; i++) {
            sum = sum + arr[i] - arr[i-k];
            minSum = Math.min(minSum, sum);
        }
        return minSum;
    }

    /**
     * Computes the minimum sum of any contiguous subarray of length k, using long for sums to avoid overflow.
     * @param arr The input array (non-null, non-empty).
     * @param k The subarray length (positive integer).
     * @return The minimum sum, or -1 if arr.length < k.
     * @throws IllegalArgumentException if arr is null/empty, k <= 0, or if the sum overflows int range.
     */
    public int minSum(int[] arr, int k) {
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
        long minSum = sum;

        for (int i = k; i < arr.length; i++) {
            sum = sum + arr[i] - arr[i - k];
            minSum = Math.min(minSum, sum);
        }

        if (minSum > Integer.MAX_VALUE || minSum < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Sum overflows int range");
        }
        return (int) minSum;
    }
}
