package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FirstOccurrenceTest {
    private FirstOccurrence firstOccurrence;

    @BeforeEach
    void setUp() {
        firstOccurrence = new FirstOccurrence();
    }

    @Test
    void should_find_first_occurrence_in_duplicates() {
        int[] arr = {1, 2, 2, 2, 3, 4};
        assertThat(firstOccurrence.findFirstOccurrence(arr, 2)).isEqualTo(1);
    }

    @Test
    void should_return_minus1_when_target_not_found_first_occurrence() {
        int[] arr = {1, 3, 5};
        assertThat(firstOccurrence.findFirstOccurrence(arr, 2)).isEqualTo(-1);
    }

    @Test
    void should_return_zero_when_all_elements_are_target_first_occurrence() {
        int[] arr = {2, 2, 2};
        assertThat(firstOccurrence.findFirstOccurrence(arr, 2)).isEqualTo(0);
    }

    @Test
    void should_return_zero_when_target_is_at_start_first_occurrence() {
        int[] arr = {2, 3, 4, 5};
        assertThat(firstOccurrence.findFirstOccurrence(arr, 2)).isEqualTo(0);
    }

    @Test
    void should_return_last_index_when_target_is_at_end_first_occurrence() {
        int[] arr = {1, 2, 3, 4};
        assertThat(firstOccurrence.findFirstOccurrence(arr, 4)).isEqualTo(3);
    }

    @Test
    void should_return_minus1_for_empty_array_first_occurrence() {
        assertThat(firstOccurrence.findFirstOccurrence(new int[]{}, 5)).isEqualTo(-1);
    }

    @Test
    void should_throw_for_null_array_first_occurrence() {
        assertThatThrownBy(() -> firstOccurrence.findFirstOccurrence(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    // --- Last Occurrence Tests ---

    @Test
    void should_find_last_occurrence_in_duplicates() {
        int[] arr = {1, 2, 2, 2, 3, 4};
        assertThat(firstOccurrence.findLastOccurrence(arr, 2)).isEqualTo(3);
    }

    @Test
    void should_return_minus1_when_target_not_found_last_occurrence() {
        int[] arr = {1, 3, 5};
        assertThat(firstOccurrence.findLastOccurrence(arr, 2)).isEqualTo(-1);
    }

    @Test
    void should_return_last_index_when_all_elements_are_target_last_occurrence() {
        int[] arr = {2, 2, 2};
        assertThat(firstOccurrence.findLastOccurrence(arr, 2)).isEqualTo(2);
    }

    @Test
    void should_return_index_when_target_is_at_start_last_occurrence() {
        int[] arr = {2, 3, 4, 5};
        assertThat(firstOccurrence.findLastOccurrence(arr, 2)).isEqualTo(0);
    }

    @Test
    void should_return_last_index_when_target_is_at_end_last_occurrence() {
        int[] arr = {1, 2, 3, 4, 4, 4};
        assertThat(firstOccurrence.findLastOccurrence(arr, 4)).isEqualTo(5);
    }

    @Test
    void should_return_minus1_for_empty_array_last_occurrence() {
        assertThat(firstOccurrence.findLastOccurrence(new int[]{}, 5)).isEqualTo(-1);
    }

    @Test
    void should_throw_for_null_array_last_occurrence() {
        assertThatThrownBy(() -> firstOccurrence.findLastOccurrence(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void should_return_first_and_last_occurrence_for_duplicates() {
        int[] arr = {1, 2, 2, 2, 3, 4};
        assertThat(firstOccurrence.findRange(arr, 2)).containsExactly(1, 3);
    }

    @Test
    void should_return_minus1_minus1_when_target_not_found_in_range() {
        int[] arr = {1, 3, 5};
        assertThat(firstOccurrence.findRange(arr, 2)).containsExactly(-1, -1);
    }

    @Test
    void should_return_zero_and_last_index_when_all_elements_are_target_in_range() {
        int[] arr = {2, 2, 2, 2};
        assertThat(firstOccurrence.findRange(arr, 2)).containsExactly(0, 3);
    }

    @Test
    void should_return_start_index_when_target_is_at_start_in_range() {
        int[] arr = {2, 3, 4, 5};
        assertThat(firstOccurrence.findRange(arr, 2)).containsExactly(0, 0);
    }

    @Test
    void should_return_end_index_when_target_is_at_end_in_range() {
        int[] arr = {1, 2, 3, 4, 4, 4};
        assertThat(firstOccurrence.findRange(arr, 4)).containsExactly(3, 5);
    }

    @Test
    void should_return_minus1_minus1_for_empty_array_in_range() {
        assertThat(firstOccurrence.findRange(new int[]{}, 5)).containsExactly(-1, -1);
    }

    @Test
    void should_throw_for_null_array_in_range() {
        assertThatThrownBy(() -> firstOccurrence.findRange(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void should_return_first_and_last_occurrence_for_duplicates_optimized() {
        int[] arr = {1, 2, 2, 2, 3, 4};
        assertThat(firstOccurrence.findRangeOptimized(arr, 2)).containsExactly(1, 3);
    }

    @Test
    void should_return_minus1_minus1_when_target_not_found_optimized() {
        int[] arr = {1, 3, 5};
        assertThat(firstOccurrence.findRangeOptimized(arr, 2)).containsExactly(-1, -1);
    }

    @Test
    void should_return_zero_and_last_index_when_all_elements_are_target_optimized() {
        int[] arr = {2, 2, 2, 2};
        assertThat(firstOccurrence.findRangeOptimized(arr, 2)).containsExactly(0, 3);
    }

    @Test
    void should_return_start_index_when_target_is_at_start_optimized() {
        int[] arr = {2, 3, 4, 5};
        assertThat(firstOccurrence.findRangeOptimized(arr, 2)).containsExactly(0, 0);
    }

    @Test
    void should_return_end_index_when_target_is_at_end_optimized() {
        int[] arr = {1, 2, 3, 4, 4, 4};
        assertThat(firstOccurrence.findRangeOptimized(arr, 4)).containsExactly(3, 5);
    }

    @Test
    void should_return_first_and_last_occurrence_for_duplicates_logN() {
        int[] arr = {1, 2, 2, 2, 3, 4};
        assertThat(firstOccurrence.findRangeLogN(arr, 2)).containsExactly(1, 3);
    }

    @Test
    void should_return_minus1_minus1_when_target_not_found_logN() {
        int[] arr = {1, 3, 5};
        assertThat(firstOccurrence.findRangeLogN(arr, 2)).containsExactly(-1, -1);
    }

    @Test
    void should_return_zero_and_last_index_when_all_elements_are_target_logN() {
        int[] arr = {2, 2, 2, 2};
        assertThat(firstOccurrence.findRangeLogN(arr, 2)).containsExactly(0, 3);
    }

    @Test
    void should_return_start_index_when_target_is_at_start_logN() {
        int[] arr = {2, 3, 4, 5};
        assertThat(firstOccurrence.findRangeLogN(arr, 2)).containsExactly(0, 0);
    }

    @Test
    void should_return_end_index_when_target_is_at_end_logN() {
        int[] arr = {1, 2, 3, 4, 4, 4};
        assertThat(firstOccurrence.findRangeLogN(arr, 4)).containsExactly(3, 5);
    }

}