package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SortedArrTest {

    private SortedArr sortedArr;

    @BeforeEach
    void setUp() {
        sortedArr = new SortedArr();
    }

    // --- Ascending Strict ---

    @Test
    void should_return_true_for_strictly_sorted_ascending() {
        assertThat(sortedArr.checkSorted(new int[]{1, 2, 3})).isTrue();
    }

    @Test
    void should_return_false_for_non_strict_ascending_when_strict_required() {
        assertThat(sortedArr.checkSorted(new int[]{1, 1, 2})).isFalse();
    }

    // --- Ascending Non-Strict ---

    @Test
    void should_return_true_for_non_strict_ascending() {
        assertThat(sortedArr.checkSorted(new int[]{1, 1, 2}, true, false)).isTrue();
    }

    @Test
    void should_return_false_for_unsorted_array_in_ascending() {
        assertThat(sortedArr.checkSorted(new int[]{3, 1, 2}, true, false)).isFalse();
    }

    // --- Descending Strict ---

    @Test
    void should_return_true_for_strictly_descending() {
        assertThat(sortedArr.checkSorted(new int[]{5, 3, 1}, false, true)).isTrue();
    }

    @Test
    void should_return_false_for_non_strict_descending_when_strict_required() {
        assertThat(sortedArr.checkSorted(new int[]{5, 4, 4, 2}, false, true)).isFalse();
    }

    // --- Descending Non-Strict ---

    @Test
    void should_return_true_for_non_strict_descending() {
        assertThat(sortedArr.checkSorted(new int[]{5, 4, 4, 2}, false, false)).isTrue();
    }

    @Test
    void should_return_false_for_unsorted_array_in_descending() {
        assertThat(sortedArr.checkSorted(new int[]{2, 5, 3}, false, false)).isFalse();
    }

    // --- Boundaries ---

    @Test
    void should_return_true_for_empty_array() {
        assertThat(sortedArr.checkSorted(new int[]{})).isTrue();
    }

    @Test
    void should_return_true_for_single_element_array() {
        assertThat(sortedArr.checkSorted(new int[]{42})).isTrue();
    }

    @Test
    void should_throw_for_null_array() {
        assertThatThrownBy(() -> sortedArr.checkSorted(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }
}
