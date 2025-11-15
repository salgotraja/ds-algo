package org.js.array.practice;

import java.util.*;

/**
 * Find all unique triplets in an array that sum to zero.
 * Solution approach:
 * 1. Sort the array to handle duplicates
 * 2. Fix one number and use two pointers for the remaining sum
 * 3. Skip duplicates to ensure unique triplets
 * <p>
 * Example:
 * Input: [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * <p>
 * Edge cases:
 * - Array with no valid triplets
 * - Array with duplicate elements
 * - Null or empty array
 */
public class ThreeSum {
    public static void main(String[] args) {
        ThreeSum threeSum = new ThreeSum();
        int[] arr = new int[] {-1, 0, 1, 2, -1, -4};
        System.out.println("Triplets: " + Arrays.deepToString(threeSum.findTripletsDuplicate(arr, 0)));
        System.out.println("Triplets: " + threeSum.findTripletsBrute(arr, 0));
        System.out.println("Triplets: " + threeSum.triplet(arr, 0));
        System.out.println("Triplets: " + threeSum.triplets(arr, 0));
    }

    // Time: O(N³), where N = array size
    // Space: O(1) excluding result storage
    public List<List<Integer>> findTripletsBrute(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Arrays can not be null");
        }

        if (arr.length < 3) {
            throw new IllegalArgumentException("Minimum size of array should be 3");
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if(arr[i] + arr[j] + arr[k] == target) {
                        result.add(Arrays.asList(arr[i], arr[j], arr[k]));
                    }
                }
            }
        }

        if (result.isEmpty()) return new ArrayList<>();

        return result;
    }

    // Duplicate triplets
    // Time Complexity: O(N3 * log(no. of unique triplets)), where N = size of the array.
    // Space Complexity: O(2 * no. of the unique triplets) as we are using a set data structure and a list to store the triplets.
    public int[][] findTripletsDuplicate(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Arrays can not be null");
        }

        if (arr.length < 3) {
            throw new IllegalArgumentException("Minimum size of array should be 3");
        }

        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (arr[i] + arr[j] + arr[k] == target) {
                        result.add(new int[] {arr[i], arr[j], arr[k]});
                    }
                }
            }
        }

        if (result.isEmpty()) {
            return new int[][]{};
        }

        return result.toArray(new int[0][]);
    }

    // Time Complexity: O(N3 * log(no. of unique triplets)), where N = size of the array.
    // Space Complexity: O(2 * no. of the unique triplets) as we are using a set data structure and a list to store the triplets.
    public List<List<Integer>> triplet(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Arrays can not be null");
        }

        if (arr.length < 3) {
            throw new IllegalArgumentException("Minimum size of array should be 3");
        }

        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j + 1; k < arr.length; k++) {
                    if (arr[i] + arr[j] + arr[k] == target) {
                        List<Integer> temp = Arrays.asList(arr[i], arr[j], arr[k]);
                        temp.sort(null);
                        result.add(temp);
                    }
                }
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * Finds all unique triplets in the array that sum up to the target value.
     * Uses a two-pointer approach after sorting to achieve better efficiency.
     *
     * Time Complexity: O(N²), where N is the size of the array
     * - O(NlogN) for sorting
     * - O(N²) for the two-pointer approach
     *
     * Space Complexity: O(1) excluding the space used for storing results
     * - Only constant extra space is used for processing
     * - Result space varies based on number of triplets found
     *
     * @param arr array to search triplets in
     * @param target the sum that triplets should add up to
     * @return List of Lists containing all unique triplets that sum to target
     * @throws IllegalArgumentException if array is null or has less than 3 elements
     */
    public List<List<Integer>> triplets(int[] arr, int target) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (arr.length < 3) {
            throw new IllegalArgumentException("Minimum size of array should be 3");
        }

        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue; // skip duplicate starting elements
            }

            int left = i + 1;
            int right = arr.length - 1;

            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];

                if (sum == target) {
                    result.add(Arrays.asList(arr[i], arr[left], arr[right]));

                    // skip duplicates for left
                    while (left < right && arr[left] == arr[left + 1]) {
                        left++;
                    }
                    // skip duplicates for right
                    while (left < right && arr[right] == arr[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }


    /*public List<List<Integer>> findTriplets(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }*/
}
