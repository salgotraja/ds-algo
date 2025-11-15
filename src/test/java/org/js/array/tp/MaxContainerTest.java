package org.js.array.tp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MaxContainerTest {

    private final MaxContainer solver = new MaxContainer();
    private final Random rnd = new Random(12345);

    // brute-force helper for verification
    private int maxAreaBrute(int[] h) {
        int n = h.length;
        int best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int area = (j - i) * Math.min(h[i], h[j]);
                if (area > best) best = area;
            }
        }
        return best;
    }

    @Test
    @DisplayName("Null input should throw IllegalArgumentException")
    void nullInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> solver.maxArea(null));
    }

    @Test
    @DisplayName("Minimum length 2 arrays")
    void minLengthTwo() {
        assertEquals(1, solver.maxArea(new int[]{1,1}));
        assertEquals(0, solver.maxArea(new int[]{0,5}));
        assertEquals(2, solver.maxArea(new int[]{2,3,1})); // corrected expected value
    }


    @Test
    @DisplayName("All zeros -> area 0")
    void allZeros() {
        int[] h = new int[10];
        assertEquals(0, solver.maxArea(h));
    }

    @Test
    @DisplayName("Strictly increasing heights")
    void increasingHeights() {
        int[] h = {1,2,3,4,5};
        assertEquals(maxAreaBrute(h), solver.maxArea(h));
    }

    @Test
    @DisplayName("Strictly decreasing heights")
    void decreasingHeights() {
        int[] h = {5,4,3,2,1};
        assertEquals(maxAreaBrute(h), solver.maxArea(h));
    }

    @Test
    @DisplayName("Plateau heights")
    void plateau() {
        int[] h = {4,4,4,4};
        assertEquals(12, solver.maxArea(h));
    }

    @Test
    @DisplayName("Given example")
    void example() {
        int[] h = {1,8,6,2,5,4,8,3,7};
        assertEquals(49, solver.maxArea(h));
    }

    @Test
    @DisplayName("Random small arrays compared with brute-force")
    void randomSmallComparison() {
        for (int t = 0; t < 200; t++) {
            int n = rnd.nextInt(10) + 2; // length between 2 and 11
            int[] h = new int[n];
            for (int i = 0; i < n; i++) h[i] = rnd.nextInt(100); // 0..99
            int expected = maxAreaBrute(h);
            int actual = solver.maxArea(h);
            assertEquals(expected, actual, "Mismatch for input: " + java.util.Arrays.toString(h));
        }
    }

    @Test
    @DisplayName("Large input sanity: runs quickly and returns non-negative")
    void largeInputSanity() {
        int n = 200000; // within constraint up to 1e5; use 200k to ensure performance margin
        int[] h = new int[n];
        for (int i = 0; i < n; i++) {
            h[i] = (i % 2 == 0) ? 10000 : 0; // alternating tall and zero
        }
        int res = solver.maxArea(h);
        assertTrue(res >= 0);
    }
}
