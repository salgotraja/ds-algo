package org.js.array.sw;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Given: An array of integers of size N and a window of size k
 * <p>
 * Expectation: Starting from the 0th index for every window finds its first negative number.
 * <p>
 * Examples:
 * Input: arr= [12, -1, -7, 8, -15, 30, 16, 28], k = 3
 * Output: [-1, -1, -7, -15, -15, 0]
 */
public class FirstNegativeNumber {
    public static void main(String[] args) {
        FirstNegativeNumber first = new FirstNegativeNumber();
        int[] arr = {12, -1, -7, 8, -15, 30, 16, 28};
        int k = 3;

        //System.out.println(Arrays.toString(first.findNegative(arr, k)));
        System.out.println(Arrays.toString(first.findFirstNegative(arr, k)));
    }

    // Brute force implementation O(n2)
    public int[] findNegative(int[] arr, int k) {
        if (arr == null) throw new IllegalArgumentException("arr cannot be null");
        int n = arr.length;
        if (n < k) return new int[0];

        int[] res = new int[n - k + 1];
        int idx = 0;
        for (int i = 0; i <= n - k; i++) {
            int firstNeg = 0;
            for (int j = i; j < i + k; j++) {
                if (arr[j] < 0) { firstNeg = arr[j]; break; }
            }
            res[idx++] = firstNeg;
        }
        return res;
    }

    public int[] findFirstNegative(int[] arr, int k) {
        if (arr == null) throw new IllegalArgumentException("arr cannot be null");
        if (k <= 0) throw new IllegalArgumentException("k must be > 0");
        int n = arr.length;
        if (n < k) return new int[0];

        int[] result = new int[n - k + 1];
        int ri = 0;
        Deque<Integer> dq = new ArrayDeque<>();

        int left = 0;
        for (int right = 0; right < n; right++) {
            if (arr[right] < 0) {
                dq.offerLast(right);
            }

            // when window size reached
            if (right - left + 1 == k) {
                if (dq.isEmpty()) {
                    result[ri++] = 0;
                } else {
                    result[ri++] = arr[dq.peekFirst()];
                    // if the first negative index is leaving the window, pop it
                    if (dq.peekFirst() == left) {
                        dq.pollFirst();
                    }
                }
                left++; // slide window
            }
        }
        return result;
    }
}
