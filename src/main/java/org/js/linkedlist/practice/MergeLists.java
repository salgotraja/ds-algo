package org.js.linkedlist.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Problem: Merge two sorted linked lists.
 * Input/Output: Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 * Edge Cases/Boundaries: One list empty.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both list1 and list2 are sorted in non-decreasing order.
 */
public class MergeLists {
    public static void main(String[] args) {
        MergeLists merge = new MergeLists();
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        System.out.println("List 1: " + list1);
        System.out.println("List 2: " + list2);
        System.out.println("Merged list: " + merge.mergeTwoLists(list1, list2));
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode n = new ListNode();
        ListNode t = n;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                t.next = list1;
                list1 = list1.next;
            } else {
                t.next = list2;
                list2 = list2.next;
            }

            t = t.next;
        }

        t.next = (list1 != null) ? list1 : list2;

        return n.next;
    }

    public ListNode mergeTwoListsRecursive(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val <= l2.val) {
            l1.next = mergeTwoListsRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsRecursive(l1, l2.next);
            return l2;
        }
    }

    public ListNode mergeTwoListsBrute(ListNode list1, ListNode list2) {

        List<Integer> arr = new ArrayList<>();
        ListNode temp1 = list1;
        ListNode temp2 = list2;

        while (temp1 != null) {
            arr.add(temp1.val);
            temp1 = temp1.next;
        }

        while (temp2 != null) {
            arr.add(temp2.val);
            temp2 = temp2.next;
        }

        Collections.sort(arr);

        ListNode n = new ListNode();
        ListNode temp = n;

        for (Integer integer : arr) {
            temp.next = new ListNode(integer);
            temp = temp.next;
        }

        return n.next;
    }
}
