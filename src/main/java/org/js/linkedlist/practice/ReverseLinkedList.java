package org.js.linkedlist.practice;

import java.util.Stack;

/**
 * Problem: Reverse a singly linked list.
 * Input/Output: Input: 1->2->3->4->null
 * Output: 4->3->2->1->null
 * Edge Cases/Boundaries: Empty list; single node; long list.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in the list is the range [0, 5000].
 * -5000 <= Node.val <= 5000
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ReverseLinkedList reverse = new ReverseLinkedList();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);

        System.out.println("Original List: " + head);

        head = reverse.reverse(head);
        System.out.println("Reversed list: " + head);
    }

    //Inefficient
    public ListNode reverseBrute(ListNode head) {
        ListNode temp = head;

        Stack<Integer> stack = new Stack<>();

        while (temp != null) {
            stack.push(temp.val);
            temp = temp.next;
        }

        temp = head;
        while (temp != null) {
            temp.val = stack.pop();
            temp = temp.next;
        }

        return head;
    }

    public ListNode reverse(ListNode head) {
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
