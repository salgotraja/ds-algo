package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SearchRotatedArrayTest {

    private SearchRotatedArray search;

    @BeforeEach
    void setUp() {
        search = new SearchRotatedArray();
    }

    @Test
    void should_find_target_in_rotated_array() {
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        assertThat(search.findIndex(arr, 0)).isEqualTo(4);
    }

    @Test
    void should_return_minus1_when_target_not_found() {
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        assertThat(search.findIndex(arr, 3)).isEqualTo(-1);
    }

    @Test
    void should_handle_no_rotation() {
        int[] arr = {1, 2, 3, 4, 5};
        assertThat(search.findIndex(arr, 3)).isEqualTo(2);
    }

    @Test
    void should_handle_full_rotation() {
        int[] arr = {1, 2, 3, 4, 5}; // same as no rotation
        assertThat(search.findIndex(arr, 5)).isEqualTo(4);
    }

    @Test
    void should_handle_single_element_array_found() {
        int[] arr = {42};
        assertThat(search.findIndex(arr, 42)).isEqualTo(0);
    }

    @Test
    void should_handle_single_element_array_not_found() {
        int[] arr = {42};
        assertThat(search.findIndex(arr, 5)).isEqualTo(-1);
    }

    @Test
    void should_return_minus1_for_empty_array() {
        assertThat(search.findIndex(new int[]{}, 5)).isEqualTo(-1);
    }

    @Test
    void should_throw_for_null_array() {
        assertThatThrownBy(() -> search.findIndex(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    // --- Duplicate cases (LeetCode #81 style) ---

    @Test
    void should_find_target_in_array_with_duplicates() {
        int[] arr = {2, 5, 6, 0, 0, 1, 2};
        assertThat(search.findIndexWithDuplicates(arr, 0)).isGreaterThanOrEqualTo(3)
                .isLessThanOrEqualTo(4);
    }

    @Test
    void should_return_minus1_when_target_not_found_in_duplicates() {
        int[] arr = {2, 5, 6, 0, 0, 1, 2};
        assertThat(search.findIndexWithDuplicates(arr, 3)).isEqualTo(-1);
    }

    @Test
    void should_find_target_when_all_elements_same() {
        int[] arr = {2, 2, 2, 2, 2};
        assertThat(search.findIndexWithDuplicates(arr, 2)).isBetween(0, arr.length - 1);
    }

    @Test
    void should_return_minus1_when_all_elements_same_but_target_not_found() {
        int[] arr = {2, 2, 2, 2, 2};
        assertThat(search.findIndexWithDuplicates(arr, 3)).isEqualTo(-1);
    }
}
