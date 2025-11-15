package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReverseArrayTest {
    private ReverseArray reverseArray;

    @BeforeEach
    void setUp() {
        reverseArray = new ReverseArray();
    }

    @Test
    void should_reverse_array() {
        int[] arr = {1, 2, 3, 4};
        reverseArray.reverseInPlace(arr);
        assertThat(arr).containsExactly(4, 3, 2, 1);
    }

    @Test
    void should_handle_single_element() {
        int[] arr = {42};
        reverseArray.reverseInPlace(arr);
        assertThat(arr).containsExactly(42);
    }

    @Test
    void should_handle_empty_array() {
        int[] arr = {};
        reverseArray.reverseInPlace(arr);
        assertThat(arr).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null() {
        assertThatThrownBy(() -> reverseArray.reverseInPlace(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }
}