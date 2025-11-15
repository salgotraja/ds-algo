package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TwoSumTest {

    private TwoSum twoSum;

    @BeforeEach
    void setUp() {
        twoSum = new TwoSum();
    }

    // --- Brute Force Tests ---

    @Test
    void should_find_pair_with_brute_force() {
        int[] arr = {2, 7, 11, 15};
        assertThat(twoSum.findTwoSumsBrute(arr, 9)).containsExactly(0, 1);
    }

    @Test
    void should_return_minus1_minus1_when_no_pair_brute_force() {
        int[] arr = {1, 2, 3};
        assertThat(twoSum.findTwoSumsBrute(arr, 100)).containsExactly(-1, -1);
    }

    // --- Hashing Tests ---

    @Test
    void should_find_pair_with_hashing() {
        int[] arr = {2, 7, 11, 15};
        assertThat(twoSum.findTwoSum(arr, 9)).containsExactly(0, 1);
    }

    @Test
    void should_return_minus1_minus1_when_no_pair_hashing() {
        int[] arr = {1, 2, 3};
        assertThat(twoSum.findTwoSum(arr, 100)).containsExactly(-1, -1);
    }

    @Test
    void should_handle_duplicate_values_with_hashing() {
        int[] arr = {3, 3};
        assertThat(twoSum.findTwoSum(arr, 6)).containsExactly(0, 1);
    }

    @Test
    void should_throw_for_null_array_hashing() {
        assertThatThrownBy(() -> twoSum.findTwoSum(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array can not be null or empty");
    }

    // --- Two Pointer Tests (Sorted Array) ---

    @Test
    void should_find_pair_in_sorted_array_two_pointers() {
        int[] arr = {0, 1, 2, 3, 5, 8};
        assertThat(twoSum.twoSumSorted(arr, 8)).containsExactly(0, 5);
    }

    @Test
    void should_return_minus1_minus1_when_no_pair_two_pointers() {
        int[] arr = {1, 2, 4, 6};
        assertThat(twoSum.twoSumSorted(arr, 20)).containsExactly(-1, -1);
    }

    @Test
    void should_handle_duplicates_in_sorted_array_two_pointers() {
        int[] arr = {1, 2, 2, 3};
        int[] result = twoSum.twoSumSorted(arr, 4);

        assertThat(arr[result[0]] + arr[result[1]])
                .isEqualTo(4);

        assertThat(result[0]).isNotEqualTo(result[1]);
    }

}
