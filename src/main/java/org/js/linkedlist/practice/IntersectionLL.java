package org.js.linkedlist.practice;

/**
 * Problem: Find intersection node.
 * Input/Output: Input: A=4->1->8->4->5, B=5->6->1->8->4->5
 * Output: Node with value 8
 * Edge Cases/Boundaries: No intersection.
 * <p>
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Intersected at '8'
 * Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5].
 * There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 * - Note that the intersected node's value is not 1 because the nodes with value 1 in A and B
 * (2nd node in A and 3rd node in B) are different node references. In other words, they point to two different locations in memory,
 * while the nodes with value 8 in A and B (3rd node in A and 4th node in B) point to the same location in memory.
 * <p>
 * Constraints:
 * <p>
 * The number of nodes of listA is in the m.
 * The number of nodes of listB is in the n.
 * 1 <= m, n <= 3 * 104
 * 1 <= Node.val <= 105
 * 0 <= skipA <= m
 * 0 <= skipB <= n
 * intersectVal is 0 if listA and listB do not intersect.
 * intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.
 * <p>
 *
 * Follow up: Could you write a solution that runs in O(m + n) time and use only O(1) memory?
 */
public class IntersectionLL {
    static void main() {
        IntersectionLL solution = new IntersectionLL();

        // Test case 1: Intersecting lists (expected: 8)
        // Common tail: 8 -> 4 -> 5
        ListNode common = new ListNode(8);
        common.next = new ListNode(4);
        common.next.next = new ListNode(5);

        // List A: 4 -> 1 -> 8 -> 4 -> 5
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = common;

        // List B: 5 -> 6 -> 1 -> 8 -> 4 -> 5
        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = common;

        ListNode result = solution.getIntersectionNode(headA, headB);
        System.out.println("Efficient: Intersection at " + (result != null ? result.val : "null"));

        result = solution.getIntersectionNodeBrute(headA, headB);
        System.out.println("Brute: Intersection at " + (result != null ? result.val : "null"));

        // Test case 2: No intersection (expected: null)
        ListNode headANoIntersect = new ListNode(1);
        headANoIntersect.next = new ListNode(2);

        ListNode headBNoIntersect = new ListNode(3);
        headBNoIntersect.next = new ListNode(4);

        result = solution.getIntersectionNode(headANoIntersect, headBNoIntersect);
        System.out.println("Efficient (no intersect): " + (result != null ? result.val : "null"));

        result = solution.getIntersectionNodeBrute(headANoIntersect, headBNoIntersect);
        System.out.println("Brute (no intersect): " + (result != null ? result.val : "null"));

        // Test case 3: One list null (expected: null)
        result = solution.getIntersectionNode(null, headA);
        System.out.println("Efficient (null list): " + (result != null ? result.val : "null"));

        result = solution.getIntersectionNodeBrute(null, headA);
        System.out.println("Brute (null list): " + (result != null ? result.val : "null"));
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode n1 = headA;
        ListNode n2 = headB;

        while (n1 != n2) {
            n1 = n1 == null ? headB : n1.next;
            n2 = n2 == null ? headA : n2.next;
        }

        return n1;
    }

    public ListNode getIntersectionNodeBrute(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        while (headB != null) {
            ListNode temp = headA;
            while (temp != null) {
                if (temp == headB) {
                    return temp;
                }
                temp = temp.next;
            }
            headB = headB.next;
        }
        return null;
    }
}
