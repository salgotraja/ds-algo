package org.js.linkedlist.practice;

/**
 * Problem: Add two numbers represented as linked lists (digits in reverse order).
 * Input/Output: Input: l1=[2,4,3], l2=[5,6,4]
 * Output: [7,0,8] (342+465=807)
 * Edge Cases/Boundaries: Different lengths; carry at end; one list empty.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 */
public class AddLists {
    public static void main(String[] args) {
        AddLists add = new AddLists();
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        System.out.println("List 1: " + l1);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        System.out.println("List 2: " + l2);
        System.out.println("Sum of l1, l2: " + add.addTwoNumbers(l1, l2));

    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) { sum += l1.val; l1 = l1.next; }
            if (l2 != null) { sum += l2.val; l2 = l2.next; }

            carry = sum / 10;
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
        }

        return dummy.next;
    }
}
