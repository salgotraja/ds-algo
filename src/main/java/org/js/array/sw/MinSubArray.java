package org.js.array.sw;

/**
 * Given an array of positive integers nums and a positive integer target, return the minimal length of a subarray whose sum is greater than or equal to target.
 * If there is no such subarray, return 0 instead.
 * <p>
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 * <p>
 *
 * Constraints:
 * <p>
 * 1 <= target <= 109
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 104
 */
public class MinSubArray {
    public static void main(String[] args) {
        MinSubArray min = new MinSubArray();
        int [] arr = new int[]{2,3,1,2,4,3};
        int target = 7;
        System.out.println(min.minSubArrayLen(target, arr));

        int[] arr1 = new int[]{2,3,1,2,4,3};
        int target1 = 4;
        System.out.println(min.minSubArrayLen(target1, arr1));
    }

    /**
     * Returns the minimal length of a contiguous subarray summing to >= target, or 0 if none exists.
     * @param target The target sum (1 <= target <= 10^9).
     * @param nums The array of positive integers (1 <= nums.length <= 10^5, 1 <= nums[i] <= 10^4).
     * @return The minimal length, or 0 if no such subarray.
     * @throws IllegalArgumentException if nums is null or empty.
     */
    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid array");
        }

        if (target < 1) {
            return 0;
        }

        int n = nums.length;
        int min = Integer.MAX_VALUE;
        int left = 0;
        long sum = 0;

        for (int right = 0; right < n; right++) {
            sum += nums[right];
            if (nums[right] >= target) return 1; // immediate single-element match
            while (sum >= target) {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }


    public int minSubArrayLenWhile(int target, int[] nums) {
        if (nums == null || nums.length == 0) throw new IllegalArgumentException("Invalid array");
        if (target < 1) return 0;

        int n = nums.length;
        int min = Integer.MAX_VALUE;
        int left = 0, right = 0;
        long sum = 0;

        while (right < n) {
            // expand right
            sum += nums[right];
            right++;

            // shrink left while we meet the target
            while (sum >= target) {
                min = Math.min(min, right - left); // right is exclusive, so length = right - left
                sum -= nums[left];
                left++;
            }
        }

        return min == Integer.MAX_VALUE ? 0 : min;
    }

}
