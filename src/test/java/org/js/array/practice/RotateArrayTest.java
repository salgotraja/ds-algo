package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RotateArrayTest {
    private RotateArray rotate;

    @BeforeEach
    void setUp() {
        rotate = new RotateArray();
    }

    @Test
    void should_rotate_right_by_k_steps_and_not_mutate_input() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int[] originalCopy = arr.clone();

        int[] rotated = rotate.rotateArray(arr, 3);

        assertThat(rotated).containsExactly(5, 6, 7, 1, 2, 3, 4);
        assertThat(arr).containsExactly(originalCopy);
    }

    @Test
    void should_handle_k_equals_zero_copy_variant() {
        int[] arr = {1, 2, 3, 4};
        int[] rotated = rotate.rotateArray(arr, 0);
        assertThat(rotated).containsExactly(1, 2, 3, 4);
    }

    @Test
    void should_handle_k_equals_n_copy_variant() {
        int[] arr = {10, 20, 30};
        int[] rotated = rotate.rotateArray(arr, 3);
        assertThat(rotated).containsExactly(10, 20, 30);
    }

    @Test
    void should_handle_k_greater_than_n_copy_variant() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] rotated = rotate.rotateArray(arr, 12); // 12 % 5 = 2
        assertThat(rotated).containsExactly(4, 5, 1, 2, 3);
    }

    @Test
    void should_support_negative_steps_as_left_rotation_copy_variant() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] rotated = rotate.rotateArray(arr, -2); // left by 2 => right by 3
        assertThat(rotated).containsExactly(3, 4, 5, 1, 2);
    }

    @Test
    void should_handle_single_element_array_copy_variant() {
        int[] arr = {42};
        int[] rotated = rotate.rotateArray(arr, 5);
        assertThat(rotated).containsExactly(42);
    }

    @Test
    void should_handle_empty_array_copy_variant() {
        int[] arr = {};
        int[] rotated = rotate.rotateArray(arr, 3);
        assertThat(rotated).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null_copy_variant() {
        assertThatThrownBy(() -> rotate.rotateArray(null, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }

    @Test
    void should_rotate_in_place_right_by_k() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        rotate.rotateInPlace(arr, 3);
        assertThat(arr).containsExactly(5, 6, 7, 1, 2, 3, 4);
    }

    @Test
    void should_not_change_array_when_k_zero_in_place() {
        int[] arr = {1, 2, 3, 4};
        rotate.rotateInPlace(arr, 0);
        assertThat(arr).containsExactly(1, 2, 3, 4);
    }

    @Test
    void should_not_change_array_when_k_is_multiple_of_n_in_place() {
        int[] arr = {9, 8, 7};
        rotate.rotateInPlace(arr, 6); // 6 % 3 == 0
        assertThat(arr).containsExactly(9, 8, 7);
    }

    @Test
    void should_handle_k_greater_than_n_in_place() {
        int[] arr = {1, 2, 3, 4, 5};
        rotate.rotateInPlace(arr, 7); // 7 % 5 == 2
        assertThat(arr).containsExactly(4, 5, 1, 2, 3);
    }

    @Test
    void should_support_negative_steps_in_place() {
        int[] arr = {1, 2, 3, 4, 5};
        rotate.rotateInPlace(arr, -1); // left by 1 => right by 4
        assertThat(arr).containsExactly(2, 3, 4, 5, 1);
    }

    @Test
    void should_handle_single_element_in_place() {
        int[] arr = {42};
        rotate.rotateInPlace(arr, 100);
        assertThat(arr).containsExactly(42);
    }

    @Test
    void should_handle_empty_array_in_place() {
        int[] arr = {};
        rotate.rotateInPlace(arr, 3);
        assertThat(arr).isEmpty();
    }

    @Test
    void should_throw_when_array_is_null_in_place() {
        assertThatThrownBy(() -> rotate.rotateInPlace(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Array cannot be null");
    }
}