package org.js.array.practice;

import java.util.*;

/**
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [1], k = 1
 * Output: [1]
 * <p>
 * Example 3:
 * <p>
 * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
 * Output: [1,2]
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 * <p>
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class TopKFrequentEle {
    public static void main(String[] args) {
        TopKFrequentEle top = new TopKFrequentEle();
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(Arrays.toString(top.topKFrequent(nums, k)));
        System.out.println(Arrays.toString(top.topKFrequentHeap(nums, k)));

        int[] nums1 = {1,2,1,2,1,2,3,1,3,2};
        int k1 = 2;
        System.out.println(Arrays.toString(top.topKFrequent(nums1, k1)));
        System.out.println(Arrays.toString(top.topKFrequentHeap(nums1, k1)));
    }

    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null) {
            throw new IllegalArgumentException("Invalid array");
        }

        int n = nums.length;
        if (n == 0) return new int[0];

        Map<Integer, Integer> freq = new HashMap<>();

        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[n + 1];

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
           int val = entry.getKey();
           int f = entry.getValue();
           if (buckets[f] == null) {
               buckets[f] = new ArrayList<>();
           }
           buckets[f].add(val);
        }

        int [] result = new int[k];
        int idx = 0;

        for(int f = n; f >= 1 && idx < k; f--) {
            if (buckets[f] == null) continue;
            for (int v : buckets[f]) {
                result[idx++] = v;
                if (idx == k) break;
            }
        }

        return result;
    }

    public int[] topKFrequentHeap(int[] nums, int k) {
        if (nums == null) throw new IllegalArgumentException("Invalid array");
        int n = nums.length;
        if (n == 0) return new int[0];

        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) freq.put(num, freq.getOrDefault(num, 0) + 1);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(freq::get)
        );

        for (int key : freq.keySet()) {
            minHeap.offer(key);
            if (minHeap.size() > k) minHeap.poll();
        }

        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = minHeap.poll();
        }
        return res;
    }
}
