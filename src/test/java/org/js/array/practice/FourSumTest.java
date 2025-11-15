package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FourSumTest {

    private FourSum fourSum;

    @BeforeEach
    void setUp() {
        fourSum = new FourSum();
    }

    @Test
    void example_from_prompt() {
        int[] arr = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> res = fourSum.fourSum(arr, 0);
        assertThat(res).containsExactlyInAnyOrder(
                List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1)
        );
    }

    @Test
    void no_quadruplets() {
        int[] arr = {1, 2, 3, 4};
        List<List<Integer>> res = fourSum.fourSum(arr, 100);
        assertThat(res).isEmpty();
    }

    @Test
    void all_zeros_one_quadruplet() {
        int[] arr = {0, 0, 0, 0, 0};
        List<List<Integer>> res = fourSum.fourSum(arr, 0);
        assertThat(res).containsExactly(List.of(0, 0, 0, 0));
    }

    @Test
    void handles_duplicates_consistently() {
        int[] arr = {2, 2, 2, 2, 2};
        List<List<Integer>> res = fourSum.fourSum(arr, 8);
        assertThat(res).containsExactly(List.of(2, 2, 2, 2));
    }

    @Test
    void negative_and_positive_mix() {
        int[] arr = {-3, -1, 0, 2, 4, 5};
        List<List<Integer>> res = fourSum.fourSum(arr, 2);
        assertThat(res).containsExactlyInAnyOrder(
                List.of(-3, -1, 2, 4)
        );
    }


    @Test
    void boundary_pruning_hits() {
        int[] arr = {-10, -9, -8, 1000, 1001, 1002};
        List<List<Integer>> res = fourSum.fourSum(arr, 1993);
        // -10 + -9 + 1000 + 1012 doesn't exist; expected specific valid combos:
        assertThat(res).isEmpty();
    }

    @Test
    void brute_force_matches_optimized_on_small_array() {
        int[] arr = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> a = fourSum.fourSumBruteForce(arr, 0);
        List<List<Integer>> b = fourSum.fourSum(arr, 0);
        assertThat(a).containsExactlyInAnyOrderElementsOf(b);
    }

    @Test
    void throws_on_invalid_inputs() {
        assertThatThrownBy(() -> fourSum.fourSum(null, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> fourSum.fourSum(new int[]{1,2,3}, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void bruteForce_and_optimized_should_match(int target) {
        int[] arr = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> brute = fourSum.fourSumBruteForce(arr, target);
        List<List<Integer>> opt   = fourSum.fourSum(arr, target);

        assertThat(opt).containsExactlyInAnyOrderElementsOf(brute);
    }

    @Test
    void brute_force_example() {
        int[] a = {1, 0, -1, 0, -2, 2};
        assertThat(fourSum.fourSumBruteForce(a, 0)).containsExactlyInAnyOrder(
                List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1)
        );
    }

    @Test
    void brute_force_no_solution() {
        int[] a = {1, 2, 3, 4};
        assertThat(fourSum.fourSumBruteForce(a, 100)).isEmpty();
    }

    @Test
    void brute_force_all_zeros_single_quad() {
        int[] a = {0, 0, 0, 0, 0};
        assertThat(fourSum.fourSumBruteForce(a, 0)).containsExactly(List.of(0, 0, 0, 0));
    }

    @Test
    void brute_force_handles_large_values() {
        int[] a = {1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000};
        assertThat(fourSum.fourSumBruteForce(a, 0)).containsExactly(
                List.of(-1_000_000_000, -1_000_000_000, 1_000_000_000, 1_000_000_000)
        );
    }

    @Test
    void optimized_matches_bruteforce_on_example() {
        int[] a = {1, 0, -1, 0, -2, 2};
        List<List<Integer>> brute = fourSum.fourSumBruteForce(a, 0);
        List<List<Integer>> opt   = fourSum.fourSum(a, 0);
        assertThat(opt).containsExactlyInAnyOrderElementsOf(brute);
    }

}
