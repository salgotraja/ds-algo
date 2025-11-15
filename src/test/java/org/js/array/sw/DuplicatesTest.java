package org.js.array.sw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class DuplicatesTest {

    private final Duplicates solver = new Duplicates();

    @Test
    @DisplayName("Example 1: [1,2,3,1], k=3 -> true")
    void example1() {
        int[] nums = {1, 2, 3, 1};
        assertTrue(solver.containsNearbyDuplicate(nums, 3));
    }

    @Test
    @DisplayName("Example 2: [1,2,3,1,2,3], k=2 -> false")
    void example2() {
        int[] nums = {1, 2, 3, 1, 2, 3};
        assertFalse(solver.containsNearbyDuplicate(nums, 2));
    }

    @Test
    @DisplayName("Adjacent duplicates: [1,1], k=1 -> true")
    void adjacentDuplicates() {
        int[] nums = {1, 1};
        assertTrue(solver.containsNearbyDuplicate(nums, 1));
    }

    @Test
    @DisplayName("No duplicates at all -> false")
    void noDuplicates() {
        int[] nums = {4, 5, 6, 7, 8};
        assertFalse(solver.containsNearbyDuplicate(nums, 3));
    }

    @Test
    @DisplayName("k = 0 should return false (distinct indices cannot be within distance 0)")
    void kZero() {
        int[] nums = {1, 1, 2};
        assertFalse(solver.containsNearbyDuplicate(nums, 0));
    }

    @Test
    @DisplayName("Large k greater than length still works")
    void largeK() {
        int[] nums = {1, 2, 3, 1};
        assertTrue(solver.containsNearbyDuplicate(nums, 100)); // indices 0 and 3 are within 100
    }

    @Test
    @DisplayName("Negative numbers handled")
    void negativeNumbers() {
        int[] nums = {-1, -2, -1};
        assertTrue(solver.containsNearbyDuplicate(nums, 2));
    }

    @Test
    @DisplayName("Empty array -> IllegalArgumentException")
    void emptyArrayThrows() {
        int[] nums = {};
        assertThrows(IllegalArgumentException.class, () -> solver.containsNearbyDuplicate(nums, 1));
    }

    @Test
    @DisplayName("Null input -> IllegalArgumentException")
    void nullArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> solver.containsNearbyDuplicate(null, 1));
    }

    @Test
    @DisplayName("Various repeated pattern checks")
    void repeatedPattern() {
        int[] nums1 = {1, 2, 1, 2, 1}; // duplicates within different windows
        assertTrue(solver.containsNearbyDuplicate(nums1, 2)); // e.g., indices 2 and 4 (difference 2)

        int[] nums2 = {1, 2, 3, 4, 5, 1}; // duplicate but distance > k
        assertFalse(solver.containsNearbyDuplicate(nums2, 3)); // distance is 5
    }
}
