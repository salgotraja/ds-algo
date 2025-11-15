package org.js.linkedlist.practice;

/**
 * Problem: Detect if cycle exists.
 * Input/Output: Input: 3->2->0->-4 (cycle)
 * Output: true
 * Edge Cases/Boundaries: Empty list; single node no cycle.
 */
public class LinkedListCycle {
    public static void main(String[] args) {
        LinkedListCycle cycle = new LinkedListCycle();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        System.out.println(cycle.hasCycle(head));

        head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head.next;
        System.out.println(cycle.hasCycle(head));

        head = new ListNode(1);
        System.out.println(cycle.hasCycle(head));

        head = new ListNode(1);
        head.next = head;
        System.out.println(cycle.hasCycle(head));

    }
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                return true;
            }
        }

        return false;
    }
}
