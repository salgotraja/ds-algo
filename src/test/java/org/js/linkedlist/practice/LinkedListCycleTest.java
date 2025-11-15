package org.js.linkedlist.practice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListCycleTest {
    private final LinkedListCycle solver = new LinkedListCycle();

    private ListNode buildLinear(int... vals) {
        if (vals.length == 0) return null;
        ListNode head = new ListNode(vals[0]);
        ListNode cur = head;
        for (int i = 1; i < vals.length; i++) {
            cur.next = new ListNode(vals[i]);
            cur = cur.next;
        }
        return head;
    }

    @Test
    void emptyList() {
        assertFalse(solver.hasCycle(null));
    }

    @Test
    void singleNodeNoCycle() {
        ListNode head = new ListNode(1);
        assertFalse(solver.hasCycle(head));
    }

    @Test
    void singleNodeCycle() {
        ListNode head = new ListNode(1);
        head.next = head;
        assertTrue(solver.hasCycle(head));
    }

    @Test
    void noCycle() {
        ListNode head = buildLinear(1,2,3,4);
        assertFalse(solver.hasCycle(head));
    }

    @Test
    void withCycle() {
        ListNode head = buildLinear(3,2,0,-4);
        head.next.next.next.next = head.next; // -4 -> 2
        assertTrue(solver.hasCycle(head));
    }
}
