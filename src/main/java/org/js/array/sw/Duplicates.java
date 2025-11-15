package org.js.array.sw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j
 * in the array such that nums[i] == nums[j] and abs(i - j) <= k.
 * <p>
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * 0 <= k <= 105
 */
public class Duplicates {
    public static void main(String[] args) {
        Duplicates duplicates = new Duplicates();
        int[] nums = new int[]{1, 2, 3, 1};
        System.out.println(duplicates.containsNearbyDuplicate(nums, 3));

        nums = new int[] {1,2,3,1,2,3};
        System.out.println(duplicates.containsNearbyDuplicate(nums, 2));
    }

    /**
     * Checks if there are two distinct indices i and j in nums such that nums[i] == nums[j] and |i - j| <= k.
     * <p>
     * Constraints: 1 <= nums.length <= 10^5, -10^9 <= nums[i] <= 10^9, 0 <= k <= 10^5.
     *
     * @param nums The input array (non-null, non-empty).
     * @param k The maximum distance between indices.
     * @return true if such duplicates exist, false otherwise.
     * @throws IllegalArgumentException if nums is null or empty.
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        Set<Integer> windowSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (windowSet.contains(nums[i])) {
                return true;
            }

            windowSet.add(nums[i]);

            if (i >= k) {
                windowSet.remove(nums[i - k]);
            }
        }
        return false;
    }

    public boolean containsNearbyDuplicateMap(int[] nums, int k) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("Invalid input");
        if (k <= 0) return false;

        Map<Integer, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer prev = lastIndex.get(nums[i]);
            if (prev != null && i - prev <= k) return true;
            lastIndex.put(nums[i], i);
        }
        return false;
    }

    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid array");
        }

        Set<Integer> result = new HashSet<>();

        for (int num : nums) {
            if (result.contains(num)) {
                return true;
            }

            result.add(num);
        }
        return false;
    }

}
