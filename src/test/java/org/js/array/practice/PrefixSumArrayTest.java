package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PrefixSumArrayTest {
    private PrefixSumArray sumArray;

    @BeforeEach
    void setUp() {
        sumArray = new PrefixSumArray();
    }

    @Test
    void should_compute_prefix_sum_in_place_for_positive_numbers() {
        int[] arr = {1, 2, 3, 4};
        sumArray.prefixSum(arr);
        assertThat(arr).containsExactly(1, 3, 6, 10);
    }

    @Test
    void should_compute_prefix_sum_in_place_for_mixed_numbers() {
        int[] arr = {2, -3, 1, -5};
        sumArray.prefixSum(arr);
        assertThat(arr).containsExactly(2, -1, 0, -5);
    }

    @Test
    void should_handle_single_element_in_place() {
        int[] arr = {42};
        sumArray.prefixSum(arr);
        assertThat(arr).containsExactly(42);
    }

    @Test
    void should_handle_empty_array_in_place() {
        int[] arr = {};
        sumArray.prefixSum(arr);
        assertThat(arr).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null_in_place() {
        assertThatThrownBy(() -> sumArray.prefixSum(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void should_throw_on_integer_overflow_in_place() {
        int[] arr = {Integer.MAX_VALUE, 1};
        assertThatThrownBy(() -> sumArray.prefixSum(arr))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Integer overflow");
    }

    @Test
    void should_throw_on_integer_underflow_in_place() {
        int[] arr = {Integer.MIN_VALUE, -1};
        assertThatThrownBy(() -> sumArray.prefixSum(arr))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Integer underflow");
    }

    // Non-mutating variant: prefixSumNonMutating(int[])

    @Test
    void should_compute_prefix_sum_non_mutating_and_not_modify_input() {
        int[] arr = {1, -2, 5, -1};
        int[] original = arr.clone();

        int[] res = sumArray.prefixSumNonMutating(arr);

        assertThat(res).containsExactly(1, -1, 4, 3);
        assertThat(arr).containsExactly(original); // input unchanged
    }

    @Test
    void should_return_empty_array_for_empty_input_non_mutating() {
        int[] arr = {};
        int[] res = sumArray.prefixSumNonMutating(arr);
        assertThat(res).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null_non_mutating() {
        assertThatThrownBy(() -> sumArray.prefixSumNonMutating(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    // Simple in-place variant: prefixSum_(int[]) (no overflow checks)

    @Test
    void should_compute_prefix_sum_simple_variant() {
        int[] arr = {1, 2, 3, 4};
        sumArray.prefixSum_(arr);
        assertThat(arr).containsExactly(1, 3, 6, 10);
    }

    @Test
    void should_handle_empty_array_simple_variant() {
        int[] arr = {};
        sumArray.prefixSum_(arr);
        assertThat(arr).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null_simple_variant() {
        assertThatThrownBy(() -> sumArray.prefixSum_(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }
}