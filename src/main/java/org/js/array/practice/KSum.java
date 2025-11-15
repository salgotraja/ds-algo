package org.js.array.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Goal: find all unique k-tuples in nums that sum to target.
 * <p>
 * Pattern:
 *  - Sort the array (enables two-pointer base case and duplicate skipping).
 *  - Recurse: fix one number, reduce to (k-1)-Sum on the suffix.
 *  - Base case (k = 2): classic two-pointer on sorted subarray.
 * <p>
 * Deduplication:
 *  - At each recursion depth, skip equal values (if (i > start && nums[i] == nums[i-1]) continue;).
 *  - In the two-pointer base, after finding a pair, skip equal left/right values.
 * <p>
 * Pruning (huge perf win):
 *  - Let n = nums.length. For a given start and k:
 *      - Min possible sum with smallest k numbers:
 *        minSum = nums[start] + nums[start+1] + ... + nums[start+k-1]
 *      - Max possible sum with largest k numbers:
 *        maxSum = nums[n-1] + nums[n-2] + ... + nums[n-k]
 *      - If target < minSum → impossible; return.
 *      - If target > maxSum → impossible; return.
 * <p>
 * Overflow safety: compute partial sums as long during pruning & sum checks.
 * <p>
 * Complexity:
 *   - Time ~ O(n^(k-1)) (k levels, last level two-pointer is O(n)).
 *   - Space: recursion depth O(k) + output.
 *
 * @TODO Revisit this implementation
 */
public class KSum {

    public static void main(String[] args) {
        KSum solver = new KSum();

        int[] arr = {1, 0, -1, 0, -2, 2};
        System.out.println(solver.fourSum(arr, 0));

        int[] arr2 = {-1, 0, 1, 2, -1, -4};
        System.out.println(solver.threeSum(arr2, 0));

        int[] arr3 = {2, 7, 11, 15};
        System.out.println(solver.twoSum(arr3, 9));
    }

    public List<List<Integer>> kSum(int[] nums, int k, long target) {
        if (nums == null || nums.length < k || k < 2) return List.of();
        Arrays.sort(nums);
        return kSumRec(nums, 0, k, target);
    }

    private List<List<Integer>> kSumRec(int[] nums, int start, int k, long target) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        if (n - start < k) return res;

        long minSum = 0, maxSum = 0;
        for (int i = 0; i < k; i++) {
            minSum += nums[start + i];
            maxSum += nums[n - 1 - i];
        }
        if (target < minSum || target > maxSum) return res;

        if (k == 2) {
            twoSum(nums, start, target, res);
            return res;
        }

        for (int i = start; i <= n - k; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;

            long newTarget = target - nums[i];

            List<List<Integer>> tails = kSumRec(nums, i + 1, k - 1, newTarget);
            for (List<Integer> tail : tails) {
                List<Integer> combo = new ArrayList<>(k);
                combo.add(nums[i]);
                combo.addAll(tail);
                res.add(combo);
            }
        }
        return res;
    }

    private void twoSum(int[] nums, int start, long target, List<List<Integer>> res) {
        int l = start, r = nums.length - 1;
        while (l < r) {
            long sum = (long) nums[l] + nums[r];
            if (sum == target) {
                res.add(List.of(nums[l], nums[r]));
                int leftVal = nums[l], rightVal = nums[r];
                while (l < r && nums[l] == leftVal) l++;
                while (l < r && nums[r] == rightVal) r--;
            } else if (sum < target) {
                l++;
            } else {
                r--;
            }
        }
    }

    public List<List<Integer>> twoSum(int[] nums, long target) { return kSum(nums, 2, target); }
    public List<List<Integer>> threeSum(int[] nums, long target) { return kSum(nums, 3, target); }
    public List<List<Integer>> fourSum(int[] nums, long target) { return kSum(nums, 4, target); }
}