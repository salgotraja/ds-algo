package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MaxElementTest {
    private MaxElement maxElement;

    @BeforeEach
    void setUp() {
        maxElement = new MaxElement();
    }

    @Test
    void should_findMax_in_positive_array() {
        int[] arr = {1, 3, 7, 2, 5};
        assertThat(maxElement.findMax(arr)).isEqualTo(7);
        int [] mix_arr = {1, 3, -9, -2, 0, 1, -3};
        assertThat(maxElement.findMax(mix_arr)).isEqualTo(3);
    }

    @Test
    void should_findMax_in_negative_array() {
        int[] arr = {-5, -2, -9};
        int max = maxElement.findMax(arr);
        assertThat(max).isEqualTo(-2);
    }

    @Test
    void should_throw_exception_when_array_is_empty() {
        assertThatThrownBy(() -> maxElement.findMax(new int[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }

    @Test
    void should_handle_single_element_array() {
        int[] arr = {42};
        int max = maxElement.findMax(arr);
        assertThat(max).isEqualTo(42);
    }

    @Test
    void should_handle_all_equal_elements() {
        int[] arr = {5, 5, 5, 5};
        int max = maxElement.findMax(arr);
        assertThat(max).isEqualTo(5);
    }

    @Test
    void should_handle_duplicate_elements() {
        int[] arr = {5, 2, 4, 5};
        assertThat(maxElement.findMax(arr)).isEqualTo(5);
    }


    @Test
    void should_sort_array_ascending() {
        int[] arr = {5, 3, 8, 1, 2};
        int[] sorted = maxElement.sort(arr.clone());
        assertThat(sorted).containsExactly(1, 2, 3, 5, 8);
    }

    @Test
    void should_sort_already_sorted_array() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] sorted = maxElement.sort(arr.clone());
        assertThat(sorted).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void should_sort_descending_array() {
        int[] arr = {9, 7, 5, 3, 1};
        int[] sorted = maxElement.sort(arr.clone());
        assertThat(sorted).containsExactly(1, 3, 5, 7, 9);
    }

    @Test
    void max_from_sort_and_findMax_should_match() {
        int[] arr = {10, 4, 20, -1, 7};
        int[] sorted = maxElement.sort(arr.clone());
        int maxFromSort = sorted[sorted.length - 1];
        int maxFromFind = maxElement.findMax(arr);
        assertThat(maxFromFind).isEqualTo(maxFromSort);
    }

    @Test
    void should_findMinAndMax_in_positive_array() {
        int[] arr = {1, 3, 7, 2, 5};
        MaxElement.MinMax result = maxElement.findMinAndMax(arr);
        assertThat(result.min()).isEqualTo(1);
        assertThat(result.max()).isEqualTo(7);
    }

    @Test
    void should_findMinAndMax_in_negative_array() {
        int[] arr = {-5, -2, -9, -1};
        MaxElement.MinMax result = maxElement.findMinAndMax(arr);
        assertThat(result.min()).isEqualTo(-9);
        assertThat(result.max()).isEqualTo(-1);
    }

    @Test
    void should_findMinMax_in_mixed_array() {
        int[] arr = {3, 5, 1, 8, -2, 7};
        MaxElement.MinMax result = maxElement.findMinAndMax(arr);
        assertThat(result.min()).isEqualTo(-2);
        assertThat(result.max()).isEqualTo(8);
    }

    @Test
    void should_handle_single_element_array_for_MinAndMax() {
        int[] arr = {42};
        MaxElement.MinMax result = maxElement.findMinAndMax(arr);
        assertThat(result.min()).isEqualTo(42);
        assertThat(result.max()).isEqualTo(42);
    }

    @Test
    void should_throw_exception_when_array_is_empty_in_MinAndMax() {
        assertThatThrownBy(() -> maxElement.findMinAndMax(new int[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }

    @Test
    void should_throw_exception_when_array_is_null_in_MinAndMax() {
        assertThatThrownBy(() -> maxElement.findMinAndMax(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }

    @Test
    void should_handle_array_with_duplicates_in_MinAndMax() {
        int[] arr = {5, 5, 5, 5};
        MaxElement.MinMax result = maxElement.findMinAndMax(arr);
        assertThat(result.min()).isEqualTo(5);
        assertThat(result.max()).isEqualTo(5);
    }

    @Test
    void should_findMinAndMaxOptimized_in_mixed_array() {
        int[] arr = {100, 3, 8, -2, 45, 7, 90};
        MaxElement.MinMax result = maxElement.findMinAndMaxOptimized(arr);
        assertThat(result.min()).isEqualTo(-2);
        assertThat(result.max()).isEqualTo(100);
    }

    @Test
    void should_handle_single_element_in_optimized() {
        int[] arr = {42};
        MaxElement.MinMax result = maxElement.findMinAndMaxOptimized(arr);
        assertThat(result.min()).isEqualTo(42);
        assertThat(result.max()).isEqualTo(42);
    }

    @Test
    void should_throw_when_null_in_optimized() {
        assertThatThrownBy(() -> maxElement.findMinAndMaxOptimized(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }


}