package org.js.array.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class SecondLargestTest {

    private SecondLargest solver;

    @BeforeEach
    void setUp() {
        solver = new SecondLargest();
    }

    @Test
    void basic_case() {
        int[] a = {10, 20, 5, 8};
        assertThat(solver.secondLargestDistinct(a)).isEqualTo(10);
        assertThat(solver.secondLargestDistinctOrThrow(a)).isEqualTo(10);
    }

    @Test
    void with_duplicates() {
        int[] a = {5, 1, 5, 2, 2};
        // distinct sorted: [5,2,1] ==> second distinct is 2
        assertThat(solver.secondLargestDistinct(a)).isEqualTo(2);
    }

    @Test
    void all_equal_returns_null_or_throw() {
        int[] a = {5, 5, 5};
        assertThat(solver.secondLargestDistinct(a)).isNull();
        assertThatThrownBy(() -> solver.secondLargestDistinctOrThrow(a))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void single_element_returns_null_or_throw() {
        int[] a = {42};
        assertThat(solver.secondLargestDistinct(a)).isNull();
        assertThatThrownBy(() -> solver.secondLargestDistinctOrThrow(a))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void null_array_throws() {
        assertThatThrownBy(() -> solver.secondLargestDistinct(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> solver.secondLargestDistinctOrThrow(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negative_values() {
        int[] a = {-10, -20, -5, -5};
        // distinct sorted: [-5, -10, -20] -> second distinct is -10
        assertThat(solver.secondLargestDistinct(a)).isEqualTo(-10);
    }

    @Test
    void large_input_stability() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i % 100;  // lots of duplicates
        assertThat(solver.secondLargestDistinct(a)).isEqualTo(98); // max 99, second 98
    }
}
