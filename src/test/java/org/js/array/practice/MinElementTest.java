package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MinElementTest {

    private MinElement minElement;

    @BeforeEach
    void setUp() {
        minElement = new MinElement();
    }

    @Test
    void should_findMin_in_positive_array() {
        int[] arr = {1, 3, 7, 2, 5};
        assertThat(minElement.findMin(arr)).isEqualTo(1);
    }

    @Test
    void should_findMin_in_mixed_array() {
        int[] mixArr = {1, 3, -9, -2, 0, 1, -3};
        assertThat(minElement.findMin(mixArr)).isEqualTo(-9);
    }

    @Test
    void should_findMin_with_zero_present() {
        int[] arrWithZero = {1, 9, 0, 3, 3};
        assertThat(minElement.findMin(arrWithZero)).isEqualTo(0);
    }


    @Test
    void should_findMin_in_negative_array() {
        int[] arr = {-5, -2, -9};
        assertThat(minElement.findMin(arr)).isEqualTo(-9);
    }

    @Test
    void should_throw_exception_when_array_is_empty() {
        assertThatThrownBy(() -> minElement.findMin(new int[]{}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }

    @Test
    void should_throw_exception_when_array_is_null() {
        assertThatThrownBy(() -> minElement.findMin(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be empty or null");
    }

    @Test
    void should_handle_single_element_array() {
        int[] arr = {42};
        assertThat(minElement.findMin(arr)).isEqualTo(42);
    }

    @Test
    void should_handle_all_equal_elements() {
        int[] arr = {5, 5, 5, 5};
        assertThat(minElement.findMin(arr)).isEqualTo(5);
    }

    @Test
    void should_handle_duplicate_elements() {
        int[] arr = {5, 2, 4, 2};
        assertThat(minElement.findMin(arr)).isEqualTo(2);
    }
}