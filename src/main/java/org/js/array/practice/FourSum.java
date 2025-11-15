package org.js.array.practice;

import java.util.*;

/**
 * Finds all unique quadruplets (4 numbers) in array that sum to target value
 * Input: arr=[1,0,-1,0,-2,2], target=0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * Edge cases:
 * - Large arrays (performance)
 * - Arrays with duplicate values
 * - Empty array or null
 * - No valid quadruplets exist
 */
public class FourSum {
    public static void main(String[] args) {
        FourSum fourSum = new FourSum();
        int[] arr = new int[]{1,0,-1,0,-2,2};
        System.out.println(fourSum.fourSumBruteForce(arr, 0));
        System.out.println(fourSum.fourSum(arr, 0));
    }

    public List<List<Integer>> fourSumBruteForce(int[] arr, int target) {
        if (arr == null || arr.length < 4) {
            throw new IllegalArgumentException("Array can not be null or have less than 4 elements");
        }

        int n = arr.length;
        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        long sum = (long) arr[i] + arr[j] + arr[k] + arr[l];
                        if (sum == target) {
                            List<Integer> quad = Arrays.asList(arr[i], arr[j], arr[k], arr[l]);
                            quad.sort(null);
                            result.add(quad);
                        }
                    }
                }
            }
        }

        List<List<Integer>> out = new ArrayList<>(result);
        out.sort(Comparator
                .comparing((List<Integer> q) -> q.getFirst())
                .thenComparing(q -> q.get(1))
                .thenComparing(q -> q.get(2))
                .thenComparing(q -> q.get(3)));
        return out;
    }


    /**
     * Finds all unique quadruplets in the array that sum to the target value using a two-pointer approach.
     * <p>
     * Time Complexity: O(n³) - Three nested loops (two explicit, one while loop)
     * Space Complexity: O(1) - Excluding the space needed for output
     * <p>
     * Algorithm:
     * 1. Sort the array first
     * 2. Fix two elements using nested loops
     * 3. Use two pointers (left and right) to find remaining elements
     * 4. Skip duplicate values to avoid duplicate quadruplets
     * 5. Handle potential integer overflow using long
     *
     * @param arr    input array to search quadruplets in
     * @param target the target sum to find
     * @return List of Lists containing all unique quadruplets that sum to target
     * @throws IllegalArgumentException if array is null, empty or has less than 4 elements
     */
    public List<List<Integer>> fourSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array can not be null or empty");
        }

        if (arr.length < 4) {
            throw new IllegalArgumentException("Minimum size of array should be 4");
        }


        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();

        int n = arr.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && arr[j] == arr[j - 1]) {
                    continue;
                }
                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    long sum = (long) arr[i] + arr[j] + arr[left] + arr[right];

                    if (sum == target) {
                        result.add(Arrays.asList(arr[i], arr[j], arr[left], arr[right]));

                        while (left < right && arr[left] == arr[left + 1]) left++;
                        while (left < right && arr[right] == arr[right - 1]) right--;

                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return result;
    }


    public List<List<Integer>> fourSumOptimized(int[] arr, int target) {
        if (arr == null || arr.length < 4) {
            throw new IllegalArgumentException("Array can not be null or have less than 4 elements");
        }

        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();
        int n = arr.length;
        long T = target; // avoid repeated widening

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) continue;

            // min sum with current i
            long minI = (long)arr[i] + arr[i + 1] + arr[i + 2] + arr[i + 3];
            if (minI > T) break; // everything beyond will be larger

            // max sum with current i
            long maxI = (long)arr[i] + arr[n - 1] + arr[n - 2] + arr[n - 3];
            if (maxI < T) continue; // too small, move i forward

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && arr[j] == arr[j - 1]) continue;

                // min sum with i,j
                long minJ = (long)arr[i] + arr[j] + arr[j + 1] + arr[j + 2];
                if (minJ > T) break;

                // max sum with i,j
                long maxJ = (long)arr[i] + arr[j] + arr[n - 1] + arr[n - 2];
                if (maxJ < T) continue;

                int left = j + 1, right = n - 1;
                while (left < right) {
                    long sum = (long)arr[i] + arr[j] + arr[left] + arr[right];
                    if (sum == T) {
                        res.add(Arrays.asList(arr[i], arr[j], arr[left], arr[right]));
                        // skip dupes on left/right
                        while (left < right && arr[left] == arr[left + 1]) left++;
                        while (left < right && arr[right] == arr[right - 1]) right--;
                        left++; right--;
                    } else if (sum < T) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return res;
    }

}