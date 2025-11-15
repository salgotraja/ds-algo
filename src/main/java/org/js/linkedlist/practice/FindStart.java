package org.js.linkedlist.practice;

/**
 * Problem: Find cycle start node.
 * Input/Output: Input: 3->2->0->-4 (cycle)
 * Output: Node with value 2
 * Edge Cases/Boundaries: No cycle.
 */
public class FindStart {
    public static void main(String[] args) {
        FindStart start = new FindStart();
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;

        ListNode cycleStart = start.detectCycle(head);
        if (cycleStart != null) {
            //System.out.println(cycleStart.val);
            System.out.println(start.detectCycle(head).val);
        } else {
            System.out.println("No cycle");
        }
    }
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }

        return null;
    }
}
