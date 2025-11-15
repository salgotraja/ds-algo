package org.js.linkedlist.practice;

/**
 * Problem: Find the middle node.
 * Input/Output: Input: 1->2->3->4->5
 * Output: Node with value 3
 * Edge Cases/Boundaries: Even nodes → return 2nd middle.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is in the range [1, 100].
 * 1 <= Node.val <= 100
 */
public class MiddleNode {
    public static void main(String[] args) {
        MiddleNode node = new MiddleNode();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("Original List: " + head);

        head = node.middleNode(head);
        System.out.println("Middle: " + head);
    }

    public ListNode middleNode(ListNode head) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public ListNode middleNodeBruteForce(ListNode head) {
        if (head == null) return null;

        int count = 0;
        ListNode t = head;
        while (t != null) {
            count++;
            t = t.next;
        }

        int steps = count / 2;
        t = head;
        for (int i = 0; i < steps; i++){
            t = t.next;
        }
        return t;
    }
}
