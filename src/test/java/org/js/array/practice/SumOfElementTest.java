package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SumOfElementTest {
    private SumOfElement sum;

    @BeforeEach
    void setUp() {
        sum = new SumOfElement();
    }

    @Test
    void should_sum_positive_array() {
        int [] arr = {1, 2, 3, 4};
        assertThat(sum.sumElements(arr)).isEqualTo(10);
    }

    @Test
    void should_sum_negative_array() {
        int [] arr = {-1, -2, -3};
        assertThat(sum.sumElements(arr)).isEqualTo(-6);
    }

    @Test
    void should_handle_single_element_array() {
        int [] arr = {42};
        assertThat(sum.sumElements(arr)).isEqualTo(42);
    }

    @Test
    void should_handle_zeros() {
        int [] arr = {0, 0, 0};
        assertThat(sum.sumElements(arr)).isEqualTo(0);
    }

    @Test
    void should_return_0_for_empty_array() {
        int [] arr = {};
        assertThat(sum.sumElements(arr)).isEqualTo(0);
    }

    @Test
    void should_throw_when_null_array() {
        assertThatThrownBy(() -> sum.sumElements(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

  /*  @Test
    void should_throw_when_empty_array() {
        assertThatThrownBy(() -> sum.sumElements(new int[] {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }*/

    // Check max min boundaries

    @Test
    void should_throw_on_overflow() {
        int[] arr = {Integer.MAX_VALUE, 1};
        assertThatThrownBy(() -> sum.sumElements(arr))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Integer overflow");
    }

    @Test
    void should_throw_on_underflow() {
        int [] arr = {Integer.MIN_VALUE, -1};
        assertThatThrownBy(() -> sum.sumElements(arr))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Integer underflow");
    }
}