package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThreeSumTest {

    private ThreeSum threeSum;

    @BeforeEach
    void setUp() {
        threeSum = new ThreeSum();
    }

    @Test
    void should_find_triplets_in_mixed_array() {
        int[] arr = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = threeSum.triplets(arr, 0);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1)
        );
    }

    @Test
    void should_return_empty_when_no_triplets() {
        int[] arr = {1, 2, 3, 4};
        List<List<Integer>> result = threeSum.triplets(arr, 0);

        assertThat(result).isEmpty();
    }

    @Test
    void should_handle_all_zeroes() {
        int[] arr = {0, 0, 0, 0};
        List<List<Integer>> result = threeSum.triplets(arr, 0);

        assertThat(result).containsExactly(List.of(0, 0, 0));
    }

    @Test
    void should_handle_large_negative_and_positive_numbers() {
        int[] arr = {-1000, -500, 0, 500, 1000};
        List<List<Integer>> result = threeSum.triplets(arr, 0);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(-1000, 0, 1000),
                List.of(-500, 0, 500)
        );
    }

    @Test
    void should_return_empty_for_null_or_small_array() {
        assertThatThrownBy(() -> threeSum.triplets(null, 0))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Array cannot be null");

        //assertThat(threeSum.triplets(new int[]{1, 2}, 0)).isEmpty();
        assertThatThrownBy(() -> threeSum.triplets(new int[]{1, 2}, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum size of array should be 3");
    }

    @Test
    void should_find_triplets_with_brute_force() {
        int[] arr = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = threeSum.triplet(arr, 0);

        assertThat(result).containsExactlyInAnyOrder(
                List.of(-1, -1, 2),
                List.of(-1, 0, 1)
        );
    }
}
