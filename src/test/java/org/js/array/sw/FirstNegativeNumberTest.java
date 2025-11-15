package org.js.array.sw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class FirstNegativeNumberTest {

    private FirstNegativeNumber solver;

    @BeforeEach
    void setUp() {
        solver = new FirstNegativeNumber();
    }

    @Test
    void example_given_in_prompt() {
        int[] arr = {12, -1, -7, 8, -15, 30, 16, 28};
        int k = 3;
        int[] expected = {-1, -1, -7, -15, -15, 0};
        assertThat(solver.findFirstNegative(arr, k)).containsExactly(expected);
        assertThat(solver.findNegative(arr, k)).containsExactly(expected); // brute
    }

    @Test
    void window_size_one_returns_element_or_zero() {
        int[] arr = {1, -2, 3, -4};
        // windows: [1], [-2], [3], [-4] -> [0, -2, 0, -4]
        assertThat(solver.findFirstNegative(arr, 1))
                .containsExactly(0, -2, 0, -4);
    }


    @Test
    void all_positive_windows_return_zero() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] res = solver.findFirstNegative(arr, 2);
        // windows: [1,2], [2,3], [3,4], [4,5] -> all zeros
        assertThat(res).containsExactly(0, 0, 0, 0);
        assertThat(solver.findNegative(arr, 2)).containsExactly(0, 0, 0, 0);
    }

    @Test
    void all_negative_windows_return_firsts() {
        int[] arr = {-1, -2, -3, -4};
        int k = 2;
        // windows: [-1,-2], [-2,-3], [-3,-4] -> first negatives: -1, -2, -3
        assertThat(solver.findFirstNegative(arr, k)).containsExactly(-1, -2, -3);
    }

    @Test
    void k_equals_array_length_returns_single_window() {
        int[] arr = {1, -2, 3, -4};
        assertThat(solver.findFirstNegative(arr, arr.length)).containsExactly(-2);
        assertThat(solver.findNegative(arr, arr.length)).containsExactly(-2);
    }

    @Test
    void n_less_than_k_returns_empty() {
        int[] arr = {1, 2};
        assertThat(solver.findFirstNegative(arr, 3)).isEmpty();
        assertThat(solver.findNegative(arr, 3)).isEmpty();
    }

    @Test
    void invalid_inputs_throw() {
        assertThatThrownBy(() -> solver.findFirstNegative(null, 3))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> solver.findFirstNegative(new int[]{1, 2, 3}, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> solver.findNegative(null, 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negatives_at_edges() {
        int[] arr = {-5, 1, 2, 3, -1};
        int k = 3;
        // windows: [-5,1,2] -> -5 ; [1,2,3] -> 0 ; [2,3,-1] -> -1
        assertThat(solver.findFirstNegative(arr, k)).containsExactly(-5, 0, -1);
    }

    @Test
    void duplicates_of_same_negative_value() {
        int[] arr = {0, -1, -1, 2, -1, 3};
        int k = 3;
        // windows: [0,-1,-1] -> -1 ; [-1,-1,2] -> -1 ; [-1,2,-1] -> -1 ; [2,-1,3] -> -1
        assertThat(solver.findFirstNegative(arr, k)).containsExactly(-1, -1, -1, -1);
    }

    @Test
    void brute_and_optimized_agree_on_random_small_cases() {
        Random rnd = new Random(0);
        final int TESTS = 100;
        for (int t = 0; t < TESTS; t++) {
            int n = rnd.nextInt(8) + 1; // 1..8
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                int val = rnd.nextInt(11) - 5; // values between -5 and 5
                arr[i] = val;
            }
            int k = rnd.nextInt(n) + 1; // 1..n
            int[] brute = solver.findNegative(arr, k);
            int[] opt = solver.findFirstNegative(arr, k);
            assertThat(opt).containsExactly(brute);
        }
    }
}
