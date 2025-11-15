package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class KSumTest {

    private KSum solver;

    @BeforeEach
    void setUp() {
        solver = new KSum();
    }

    // ---------- TwoSum (k=2) ----------

    @Test
    void twoSum_basic_pair_exists() {
        int[] nums = {2, 7, 11, 15};
        List<List<Integer>> res = solver.kSum(nums, 2, 9);
        assertThat(res).containsExactlyInAnyOrder(
                List.of(2, 7)
        );
    }

    @Test
    void twoSum_no_pair() {
        int[] nums = {1, 2, 3};
        List<List<Integer>> res = solver.kSum(nums, 2, 100);
        assertThat(res).isEmpty();
    }

    @Test
    void twoSum_duplicates_handled() {
        int[] nums = {3, 3, 3};
        List<List<Integer>> res = solver.kSum(nums, 2, 6);
        assertThat(res).containsExactly(
                List.of(3, 3)
        );
    }

    // ---------- ThreeSum (k=3) ----------

    @Test
    void threeSum_example_from_prompt() {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> res = solver.kSum(nums, 3, 0);
        assertThat(res).containsExactlyInAnyOrder(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1)
        );
    }

    @Test
    void threeSum_all_zeros_single_triplet() {
        int[] nums = {0, 0, 0, 0};
        List<List<Integer>> res = solver.kSum(nums, 3, 0);
        assertThat(res).containsExactly(
                List.of(0, 0, 0)
        );
    }

    @Test
    void threeSum_no_triplets() {
        int[] nums = {1, 2, 3, 4};
        List<List<Integer>> res = solver.kSum(nums, 3, 0);
        assertThat(res).isEmpty();
    }

    // ---------- FourSum (k=4) ----------

    @Test
    void fourSum_example() {
        int[] nums = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> res = solver.kSum(nums, 4, 0);
        assertThat(res).containsExactlyInAnyOrder(
                List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1)
        );
    }

    @Test
    void fourSum_all_zeros_single_quadruplet() {
        int[] nums = {0, 0, 0, 0, 0};
        List<List<Integer>> res = solver.kSum(nums, 4, 0);
        assertThat(res).containsExactly(
                List.of(0, 0, 0, 0)
        );
    }

    @Test
    void fourSum_no_quadruplets() {
        int[] nums = {1, 2, 3, 4};
        List<List<Integer>> res = solver.kSum(nums, 4, 100);
        assertThat(res).isEmpty();
    }

    @Test
    void fourSum_negatives_and_positives_mix() {
        int[] nums = {-3, -1, 0, 2, 4, 5};
        List<List<Integer>> res = solver.kSum(nums, 4, 2);
        // Only one valid combination: -3 + -1 + 2 + 4 = 2
        assertThat(res).containsExactly(
                List.of(-3, -1, 2, 4)
        );
    }

    // ---------- Edge cases & validation ----------

    @Test
    void returns_empty_for_null_or_too_small_or_invalid_k() {
        assertThat(solver.kSum(null, 3, 0)).isEmpty();
        assertThat(solver.kSum(new int[]{1, 2}, 3, 3)).isEmpty(); // not enough elements
        assertThat(solver.kSum(new int[]{1, 2, 3}, 1, 3)).isEmpty(); // k < 2
    }

    @Test
    void handles_large_values_without_overflow() {
        int[] nums = {1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000};
        // target = 0, quadruplet should exist
        List<List<Integer>> res = solver.kSum(nums, 4, 0L);
        assertThat(res).containsExactly(
                List.of(-1_000_000_000, -1_000_000_000, 1_000_000_000, 1_000_000_000)
        );
    }

    // ---------- (Optional) If you exposed helpers twoSumAll/threeSumAll/fourSumAll ----------

    @Test
    void helpers_work_same_as_ksum() {
        int[] a = {1, 0, -1, 0, -2, 2};
        assertThat(solver.fourSum(a, 0)).containsExactlyInAnyOrder(
                List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1)
        );

        int[] b = {-1, 0, 1, 2, -1, -4};
        assertThat(solver.threeSum(b, 0)).containsExactlyInAnyOrder(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1)
        );

        int[] c = {2, 7, 11, 15};
        assertThat(solver.twoSum(c, 9)).containsExactly(
                List.of(2, 7)
        );
    }
}
