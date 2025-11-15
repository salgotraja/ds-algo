package org.js.linkedlist.practice;

import java.util.List;

/**
 * Problem: Remove the nth node from the end.
 * Input/Output: Input: 1->2->3->4->5, n=2
 * Output: 1->2->3->5
 * Edge Cases/Boundaries: Remove head; n=list length.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 */
public class RemoveNthNode {
    public static void main(String[] args) {
        RemoveNthNode remove = new RemoveNthNode();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("Original: " + head);
        int n1 = 2;
        ListNode result1 = remove.removeNthFromEnd(head, n1);
        System.out.println("After removing " + n1 + "th from end: " + result1);

        // Test Case 2: Remove head (n = length)
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        System.out.println("\nOriginal: " + head2);
        int n2 = 2;
        ListNode result2 = remove.removeNthFromEnd(head2, n2);
        System.out.println("After removing " + n2 + "th from end: " + result2);

        // Test Case 3: Single node
        ListNode head3 = new ListNode(1);
        System.out.println("\nOriginal: " + head3);
        int n3 = 1;
        ListNode result3 = remove.removeNthFromEnd(head3, n3);
        System.out.println("After removing " + n3 + "th from end: " + result3);

        // Test Case 4: Remove tail (n=1)
        ListNode head4 = new ListNode(1);
        head4.next = new ListNode(2);
        head4.next.next = new ListNode(3);
        System.out.println("\nOriginal: " + head4);
        int n4 = 1;
        ListNode result4 = remove.removeNthFromEnd(head4, n4);
        System.out.println("After removing " + n4 + "th from end: " + result4);
    }

    /**
     * Removes the nth node from the end of the list using two pointers (optimized, O(n) time, O(1) space).
     * @param head Head of the list (non-null, sz >=1).
     * @param n Position from end (1 <= n <= sz).
     * @return New head after removal.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        // Advance fast by n steps
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // If fast reached end, n == length, remove head
        if (fast == null) {
            return head.next;
        }

        // Move both until fast reaches end (slow is now before the node to remove)
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Remove the node
        slow.next = slow.next.next;

        return head;
    }

    /**
     * Brute-force version: Two passes to count length and remove (O(n) time, O(1) space).
     * Included for educational purposes.
     * @param head Head of the list.
     * @param n Position from end.
     * @return New head after removal.
     */
    public ListNode removeNthFromEndBrute(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        // First pass: Count length
        int sz = 0;
        ListNode temp = head;
        while (temp != null) {
            sz++;
            temp = temp.next;
        }

        // If n == length, remove head
        if (n == sz) {
            return head.next;
        }

        // Traverse to the node before the one to remove (sz - n - 1 steps from head)
        temp = head;
        for (int i = 0; i < sz - n - 1; i++) {
            temp = temp.next;
        }

        // Remove the node
        temp.next = temp.next.next;

        return head;
    }

}
