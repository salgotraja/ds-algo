package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FindMinRotatedArrayTest {

    private FindMinRotatedArray finder;

    @BeforeEach
    void setUp() {
        finder = new FindMinRotatedArray();
    }

    @Test
    void should_find_min_in_rotated_array() {
        int[] arr = {3,4,5,1,2};
        assertThat(finder.findMin(arr)).isEqualTo(1);
    }

    @Test
    void should_find_min_in_rotated_array_with_wraparound() {
        int[] arr = {4,5,6,7,0,1,2};
        assertThat(finder.findMin(arr)).isEqualTo(0);
    }

    @Test
    void should_find_min_in_sorted_array_no_rotation() {
        int[] arr = {1,2,3,4,5};
        assertThat(finder.findMin(arr)).isEqualTo(1);
    }

    @Test
    void should_handle_single_element() {
        int[] arr = {42};
        assertThat(finder.findMin(arr)).isEqualTo(42);
    }

    @Test
    void should_throw_for_empty_array() {
        assertThatThrownBy(() -> finder.findMin(new int[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null or empty");
    }

    @Test
    void should_throw_for_null_array() {
        assertThatThrownBy(() -> finder.findMin(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null or empty");
    }

    // --- With Duplicates ---

    @Test
    void should_find_min_with_duplicates() {
        int[] arr = {2,2,2,0,1};
        assertThat(finder.findMinWithDuplicates(arr)).isEqualTo(0);
    }

    @Test
    void should_find_min_when_all_elements_same() {
        int[] arr = {2,2,2,2};
        assertThat(finder.findMinWithDuplicates(arr)).isEqualTo(2);
    }

    @Test
    void should_find_min_when_min_at_end_with_duplicates() {
        int[] arr = {10,10,10,1,10};
        assertThat(finder.findMinWithDuplicates(arr)).isEqualTo(1);
    }
}
