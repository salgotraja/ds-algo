package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindTargetTest {
    private FindTarget findTarget;

    @BeforeEach
    void setUp() {
        findTarget = new FindTarget();
    }

    @Test
    void should_find_target_in_sorted_array() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        assertThat(findTarget.binarySearch(arr, 4)).isEqualTo(3);
    }

    @Test
    void should_return_minus1_when_target_not_found() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        assertThat(findTarget.binarySearch(arr, 10)).isEqualTo(-1);
    }

    @Test
    void should_handle_empty_array() {
        assertThat(findTarget.binarySearch(new int[]{}, 5)).isEqualTo(-1);
    }

    @Test
    void should_handle_single_element_array_found() {
        int[] arr = {42};
        assertThat(findTarget.binarySearch(arr, 42)).isEqualTo(0);
    }

    @Test
    void should_handle_single_element_array_not_found() {
        int[] arr = {42};
        assertThat(findTarget.binarySearch(arr, 5)).isEqualTo(-1);
    }

    @Test
    void should_throw_for_null_array() {
        assertThatThrownBy(() -> findTarget.binarySearch(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void should_throw_for_unsorted_array() {
        int[] arr = {3, 1, 5};
        assertThatThrownBy(() -> findTarget.binarySearch(arr, 3))
                .isInstanceOf(InputMismatchException.class)
                .hasMessage("Array must be sorted");
    }
}