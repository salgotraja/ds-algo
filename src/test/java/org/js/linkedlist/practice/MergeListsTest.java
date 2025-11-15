package org.js.linkedlist.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeListsTest {

    private final MergeLists solver = new MergeLists();
    private final Random rnd = new Random(12345);

    private static ListNode buildList(int[] vals) {
        if (vals == null || vals.length == 0) return null;
        ListNode head = new ListNode(vals[0]);
        ListNode cur = head;
        for (int i = 1; i < vals.length; i++) {
            cur.next = new ListNode(vals[i]);
            cur = cur.next;
        }
        return head;
    }

    private static int[] toArray(ListNode head) {
        List<Integer> out = new ArrayList<>();
        ListNode t = head;
        while (t != null) {
            out.add(t.val);
            t = t.next;
        }
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    @DisplayName("Both lists empty -> null")
    void bothEmpty() {
        assertNull(solver.mergeTwoLists(null, null));
        assertNull(solver.mergeTwoListsBrute(null, null));
    }

    @Test
    @DisplayName("One list empty -> return the other")
    void oneEmpty() {
        ListNode l1 = buildList(new int[]{1,2,3});
        assertArrayEquals(new int[]{1,2,3}, toArray(solver.mergeTwoLists(l1, null)));
        assertArrayEquals(new int[]{1,2,3}, toArray(solver.mergeTwoLists(null, l1)));
    }

    @Test
    @DisplayName("Simple merge example")
    void example() {
        ListNode l1 = buildList(new int[]{1,2,4});
        ListNode l2 = buildList(new int[]{1,3,4});
        ListNode merged = solver.mergeTwoLists(l1, l2);
        assertArrayEquals(new int[]{1,1,2,3,4,4}, toArray(merged));
    }

    @Test
    @DisplayName("Merged list equals brute result for small examples")
    void equalsBruteSmall() {
        ListNode l1 = buildList(new int[]{1,3,5});
        ListNode l2 = buildList(new int[]{2,4,6});
        assertArrayEquals(toArray(solver.mergeTwoListsBrute(buildList(new int[]{1,3,5}), buildList(new int[]{2,4,6}))),
                toArray(solver.mergeTwoLists(buildList(new int[]{1,3,5}), buildList(new int[]{2,4,6}))));
    }

    @Test
    @DisplayName("Duplicates and negatives")
    void duplicatesAndNegatives() {
        ListNode l1 = buildList(new int[]{-2,0,0,3});
        ListNode l2 = buildList(new int[]{-1,0,2});
        int[] expected = toArray(solver.mergeTwoListsBrute(buildList(new int[]{-2,0,0,3}), buildList(new int[]{-1,0,2})));
        assertArrayEquals(expected, toArray(solver.mergeTwoLists(buildList(new int[]{-2,0,0,3}), buildList(new int[]{-1,0,2}))));
    }

    @Test
    @DisplayName("Randomized comparisons against brute (100 trials)")
    void randomizedComparison() {
        for (int t = 0; t < 100; t++) {
            int len1 = rnd.nextInt(6); // up to 5
            int len2 = rnd.nextInt(6);
            int[] a = new int[len1];
            int[] b = new int[len2];
            for (int i = 0; i < len1; i++) a[i] = rnd.nextInt(10) - 5; // -5..4
            for (int i = 0; i < len2; i++) b[i] = rnd.nextInt(10) - 5;
            Arrays.sort(a);
            Arrays.sort(b);
            ListNode l1 = buildList(a);
            ListNode l2 = buildList(b);

            int[] expected = toArray(solver.mergeTwoListsBrute(buildList(a), buildList(b)));
            int[] actual = toArray(solver.mergeTwoLists(buildList(a), buildList(b)));
            assertArrayEquals(expected, actual, "Failed for a=" + Arrays.toString(a) + " b=" + Arrays.toString(b));
        }
    }

    @Test
    @DisplayName("Large-ish lists performance sanity")
    void largeListsSanity() {
        int n = 1000;
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) { a[i] = i * 2; b[i] = i * 2 + 1; }
        ListNode l1 = buildList(a);
        ListNode l2 = buildList(b);
        ListNode merged = solver.mergeTwoLists(l1, l2);
        assertEquals(2 * n, toArray(merged).length);
    }
}
