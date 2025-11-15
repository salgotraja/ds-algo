package org.js.linkedlist.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddListsTest {

    private final AddLists solver = new AddLists();

    // helper to build list from int array (least-significant-digit first)
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

    private static int[] toArray(ListNode head) {
        if (head == null) return new int[0];
        java.util.List<Integer> out = new java.util.ArrayList<>();
        while (head != null) {
            out.add(head.val);
            head = head.next;
        }
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    @DisplayName("Example: 342 + 465 = 807")
    void example() {
        ListNode l1 = build(2,4,3);
        ListNode l2 = build(5,6,4);
        ListNode res = solver.addTwoNumbers(l1, l2);
        assertArrayEquals(new int[]{7,0,8}, toArray(res));
    }

    @Test
    @DisplayName("Different lengths and carry propagation")
    void differentLengthsCarry() {
        assertArrayEquals(new int[]{0,0,1}, toArray(solver.addTwoNumbers(build(9,9), build(1))));
        assertArrayEquals(new int[]{0,1}, toArray(solver.addTwoNumbers(build(5), build(5))));
    }

    @Test
    @DisplayName("One list null vs empty handling")
    void oneNull() {
        assertArrayEquals(new int[]{1,2,3}, toArray(solver.addTwoNumbers(null, build(1,2,3))));
        assertArrayEquals(new int[]{1,2,3}, toArray(solver.addTwoNumbers(build(1,2,3), null)));
    }

    @Test
    @DisplayName("Zeros")
    void zeros() {
        assertArrayEquals(new int[]{0}, toArray(solver.addTwoNumbers(build(0), build(0))));
    }

    @Test
    @DisplayName("Large equal-length numbers")
    void largeAllNines() {
        // 9+9+carry chain
        ListNode a = build(9,9,9,9);
        ListNode b = build(9,9,9,9);
        ListNode res = solver.addTwoNumbers(a, b); // should be 8,9,9,9,1 => 19998
        assertArrayEquals(new int[]{8,9,9,9,1}, toArray(res));
    }
}
