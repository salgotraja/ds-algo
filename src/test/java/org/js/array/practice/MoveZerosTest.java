package org.js.array.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MoveZerosTest {

    @Test
    @DisplayName("Both implementations handle null by throwing IllegalArgumentException")
    void nullInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> MoveZeros.moveZerosOverwrite(null));
        assertThrows(IllegalArgumentException.class, () -> MoveZeros.moveZerosSwap(null));
    }

    @Test
    @DisplayName("Single-element arrays remain unchanged")
    void singleElement() {
        int[] a = {0};
        int[] b = {0};
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);

        int[] c = {5};
        int[] d = {5};
        MoveZeros.moveZerosOverwrite(c);
        MoveZeros.moveZerosSwap(d);
        assertArrayEquals(c, d);
    }

    @Test
    @DisplayName("General example and stability: order of non-zeros preserved")
    void exampleAndStability() {
        int[] input1 = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};

        int[] a = Arrays.copyOf(input1, input1.length);
        int[] b = Arrays.copyOf(input1, input1.length);

        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);

        assertArrayEquals(expected, a, "overwrite produced unexpected result");
        assertArrayEquals(expected, b, "swap produced unexpected result");
        assertArrayEquals(a, b, "Both implementations should produce identical arrays");
    }

    @Test
    @DisplayName("All zeros and no zeros cases")
    void allZerosAndNoZeros() {
        int[] allZeros = new int[10]; // default 0s
        int[] a = Arrays.copyOf(allZeros, allZeros.length);
        int[] b = Arrays.copyOf(allZeros, allZeros.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);

        int[] noZeros = {1, 2, 3, 4, 5};
        a = Arrays.copyOf(noZeros, noZeros.length);
        b = Arrays.copyOf(noZeros, noZeros.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);
    }

    @Test
    @DisplayName("Zeros at the end or start")
    void zerosAtBoundaries() {
        int[] startZeros = {0,0,1,2,3};
        int[] a = Arrays.copyOf(startZeros, startZeros.length);
        int[] b = Arrays.copyOf(startZeros, startZeros.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);

        int[] endZeros = {1,2,3,0,0};
        a = Arrays.copyOf(endZeros, endZeros.length);
        b = Arrays.copyOf(endZeros, endZeros.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);
    }

    @Test
    @DisplayName("Alternating zeros and numbers")
    void alternating() {
        int[] input = {0,1,0,2,0,3,0,4,0,5};
        int[] a = Arrays.copyOf(input, input.length);
        int[] b = Arrays.copyOf(input, input.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);
    }

    @Test
    @DisplayName("Randomized fuzzing to ensure identical behavior across many cases")
    void randomizedComparison() {
        Random rnd = new Random(12345);
        for (int t = 0; t < 500; t++) {
            int len = rnd.nextInt(20) + 1; // length 1..20
            int[] arr = new int[len];
            for (int i = 0; i < len; i++) {
                // include zeros and a spread of integers including negatives
                arr[i] = (rnd.nextInt(5) == 0) ? 0 : rnd.nextInt(21) - 10;
            }

            int[] a = Arrays.copyOf(arr, arr.length);
            int[] b = Arrays.copyOf(arr, arr.length);

            MoveZeros.moveZerosOverwrite(a);
            MoveZeros.moveZerosSwap(b);

            assertArrayEquals(a, b, "Mismatch on input: " + Arrays.toString(arr));
        }
    }

    @Test
    @DisplayName("Large input sanity check")
    void largeInputSanity() {
        int n = 10000;
        int[] arr = new int[n];
        // place a few non-zeros near the end
        for (int i = 0; i < n - 3; i++) arr[i] = 0;
        arr[n-3] = 1; arr[n-2] = 2; arr[n-1] = 3;

        int[] a = Arrays.copyOf(arr, arr.length);
        int[] b = Arrays.copyOf(arr, arr.length);
        MoveZeros.moveZerosOverwrite(a);
        MoveZeros.moveZerosSwap(b);
        assertArrayEquals(a, b);
    }
}
