package org.js.array.practice;

import java.util.Arrays;

/**
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * <p>
 * Note that you must do this in-place without making a copy of the array.
 * <p>
 *
 * Example 1:
 * <p>
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Example 2:
 * <p>
 * Input: nums = [0]
 * Output: [0]
 * <p>
 *
 * Constraints:
 * <p>
 * 1 <= nums.length <= 104
 * -231 <= nums[i] <= 231 - 1
 * <p>
 *
 * Follow up: Could you minimize the total number of operations done?
 */
public class MoveZeros {

    public static void main(String[] args) {
        int [] nums = {0, 1, 0, 3, 12};
        moveZeros(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeros(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Invalid array");
        }

        if (nums.length <= 1) return;

        int insertPos = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[insertPos++] = num;
            }
        }
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }
    public static void moveZerosOverwrite(int[] nums) {
        if (nums == null) throw new IllegalArgumentException("Invalid array");
        int n = nums.length;
        if (n <= 1) return;

        int insertPos = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                nums[insertPos++] = nums[i];
            }
        }
        while (insertPos < n) {
            nums[insertPos++] = 0;
        }
    }

    public static void moveZerosSwap(int[] nums) {
        if (nums == null) throw new IllegalArgumentException("Invalid array");
        int n = nums.length;
        if (n <= 1) return;

        int insertPos = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                if (i != insertPos) {
                    int tmp = nums[insertPos];
                    nums[insertPos] = nums[i];
                    nums[i] = tmp;
                }
                insertPos++;
            }
        }
    }
}
