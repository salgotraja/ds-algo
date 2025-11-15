package org.js.linkedlist.practice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FindStartTest {

    private final FindStart solver = new FindStart();

    private static ListNode build(int... vals) {
        if (vals == null || vals.length == 0) return null;
        ListNode head = new ListNode(vals[0]);
        ListNode cur = head;
        for (int i = 1; i < vals.length; i++) {
            cur.next = new ListNode(vals[i]);
            cur = cur.next;
        }
        return head;
    }

    /** Create a cycle: connect the tail of list to node at index `pos` (0-based).
     *  If pos == -1, keep list acyclic.
     */
    private static void makeCycle(ListNode head, int pos) {
        if (head == null || pos < 0) return;
        ListNode tail = head;
        ListNode target = null;
        int idx = 0;
        while (tail.next != null) {
            if (idx == pos) target = tail;
            tail = tail.next;
            idx++;
        }
        // check final node index
        if (idx == pos) target = tail;
        if (target != null) tail.next = target;
    }

    @Test
    void noCycle() {
        ListNode head = build(1,2,3,4);
        assertNull(solver.detectCycle(head));
    }

    @Test
    void simpleCycleInMiddle() {
        ListNode head = build(3,2,0,-4);
        makeCycle(head, 1); // connect tail -> node at index 1 (value 2)
        ListNode start = solver.detectCycle(head);
        assertNotNull(start);
        assertEquals(2, start.val);
    }

    @Test
    void singleNodeNoCycle() {
        ListNode head = build(1);
        assertNull(solver.detectCycle(head));
    }

    @Test
    void singleNodeSelfCycle() {
        ListNode head = build(1);
        makeCycle(head, 0); // tail -> head
        ListNode start = solver.detectCycle(head);
        assertNotNull(start);
        assertEquals(1, start.val);
    }

    @Test
    void twoNodeCycle() {
        ListNode head = build(1,2);
        makeCycle(head, 0); // 2 -> 1
        ListNode start = solver.detectCycle(head);
        assertNotNull(start);
        assertEquals(1, start.val);
    }
}
