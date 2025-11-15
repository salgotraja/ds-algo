package org.js.linkedlist.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseLinkedListTest {

    private final ReverseLinkedList solver = new ReverseLinkedList();

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
        if (head == null) return new int[0];
        // first compute length
        int len = 0;
        ListNode t = head;
        while (t != null) { len++; t = t.next; }
        int[] out = new int[len];
        t = head; int i = 0;
        while (t != null) { out[i++] = t.val; t = t.next; }
        return out;
    }

    @Test
    @DisplayName("Reverse of null should be null")
    void reverseNull() {
        ListNode head = null;
        ListNode res = solver.reverse(head);
        assertNull(res);
    }

    @Test
    @DisplayName("Reverse single element list remains same")
    void reverseSingle() {
        ListNode head = buildList(new int[]{5});
        ListNode res = solver.reverse(head);
        assertNotNull(res);
        assertArrayEquals(new int[]{5}, toArray(res));
    }

    @Test
    @DisplayName("Reverse multiple elements odd length")
    void reverseOdd() {
        ListNode head = buildList(new int[]{1,2,3,4,5});
        ListNode res = solver.reverse(head);
        assertArrayEquals(new int[]{5,4,3,2,1}, toArray(res));
    }

    @Test
    @DisplayName("Reverse multiple elements even length")
    void reverseEven() {
        ListNode head = buildList(new int[]{1,2,3,4});
        ListNode res = solver.reverse(head);
        assertArrayEquals(new int[]{4,3,2,1}, toArray(res));
    }

    @Test
    @DisplayName("Reverse preserves values with duplicates")
    void reverseWithDuplicates() {
        ListNode head = buildList(new int[]{1,2,2,3,2});
        ListNode res = solver.reverse(head);
        assertArrayEquals(new int[]{2,3,2,2,1}, toArray(res));
    }

    @Test
    @DisplayName("Reverse with negative values")
    void reverseNegatives() {
        ListNode head = buildList(new int[]{-1, -2, -3});
        ListNode res = solver.reverse(head);
        assertArrayEquals(new int[]{-3, -2, -1}, toArray(res));
    }

    @Test
    @DisplayName("Double reverse returns original")
    void doubleReverse() {
        int[] vals = {1,2,3,4,5,6};
        ListNode head = buildList(vals);
        ListNode r1 = solver.reverse(head);
        ListNode r2 = solver.reverse(r1);
        assertArrayEquals(vals, toArray(r2));
    }
}
