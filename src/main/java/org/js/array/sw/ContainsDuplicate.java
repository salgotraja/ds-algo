package org.js.array.sw;

import java.util.*;

/**
 * You are given an integer array nums and two integers indexDiff and valueDiff.
 * <p>
 * Find a pair of indices (i, j) such that:
 * <p>
 * i != j,
 * abs(i - j) <= indexDiff.
 * abs(nums[i] - nums[j]) <= valueDiff, and
 * Return true if such pair exists or false otherwise.
 * <p>
 *
 * Example 1:
 * <p>
 * Input: nums = [1,2,3,1], indexDiff = 3, valueDiff = 0
 * Output: true
 * Explanation: We can choose (i, j) = (0, 3).
 * We satisfy the three conditions:
 * i != j --> 0 != 3
 * abs(i - j) <= indexDiff --> abs(0 - 3) <= 3
 * abs(nums[i] - nums[j]) <= valueDiff --> abs(1 - 1) <= 0
 * <p>
 * Constraints:
 * <p>
 * 2 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * 1 <= indexDiff <= nums.length
 * 0 <= valueDiff <= 109
 */
public class ContainsDuplicate {
    public static void main(String[] args) {
        ContainsDuplicate duplicate = new ContainsDuplicate();
        int[] nums = new int[] {1, 2, 3, 1};
        int indexDiff = 3, valueDiff = 0;

        System.out.println(duplicate.containsNearbyAlmostDuplicate(nums, indexDiff, valueDiff));
    }

    public boolean containsNearbyAlmostDuplicateNaive(int[] nums, int indexDiff, int valueDiff) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (indexDiff <= 0) return false; // optional guard

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int start = Math.max(0, i - indexDiff);
            for (int j = start; j < i; j++) {
                long diff = Math.abs((long) nums[i] - (long) nums[j]);
                if (diff <= (long) valueDiff) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (indexDiff <= 0) return false; // optional

        List<Integer> window = new ArrayList<>();
        for (int cur : nums) {
            for (Integer integer : window) {
                long diff = Math.abs((long) cur - (long) integer);
                if (diff <= (long) valueDiff) {
                    return true;
                }
            }

            window.add(cur);

            if (window.size() > indexDiff) {
                window.removeFirst();
            }
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicateDeque(int[] nums, int indexDiff, int valueDiff) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("Invalid input");
        if (indexDiff <= 0) return false;

        Deque<Integer> window = new ArrayDeque<>();
        for (int num : nums) {
            for (int val : window) {
                if (Math.abs((long) num - (long) val) <= (long) valueDiff) return true;
            }
            window.addLast(num);
            if (window.size() > indexDiff) window.removeFirst();
        }
        return false;
    }

    public boolean containsNearbyAlmostDuplicateTree(int[] nums, int indexDiff, int valueDiff) {
        if (nums == null || nums.length == 0) return false;

        TreeSet<Long> window = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];

            // Check floor (<= num)
            Long floor = window.floor(num);
            if (floor != null && num - floor <= valueDiff) return true;

            // Check ceiling (>= num)
            Long ceiling = window.ceiling(num);
            if (ceiling != null && ceiling - num <= valueDiff) return true;

            window.add(num);

            if (i >= indexDiff) {
                window.remove((long) nums[i - indexDiff]);
            }
        }
        return false;
    }


}
