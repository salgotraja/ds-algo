package org.js.linkedlist.practice;

/// Problem: Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
///
/// k is a positive integer and is less than or equal to the length of the linked list.
/// If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
///
/// Input/Output: Input: 1->2->3->4->5, k=2
///
/// Output: 2->1->4->3->5
///
/// Edge Cases/Boundaries: k > list length.
///
/// Constraints:
///
/// The number of nodes in the list is n.
/// 1 <= k <= n <= 5000
/// 0 <= Node.val <= 1000
public class ReverseNodes {

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 0) {
            return null;
        }

        ListNode temp = head;

        /*for (int i = 0; i < k; i++) {
            temp
        }*/

        return null;
    }

    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        return prev;
    }
}
