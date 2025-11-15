package org.js.linkedlist.practice;

/**
 * There is a singly-linked list head and we want to delete a node in it.
 * <p>
 * You are given the node to be deleted node. You will not be given access to the first node of head.
 * <p>
 * All the values of the linked list are unique, and it is guaranteed that the given node node is not the last node in the linked list.
 * <p>
 * Delete the given node. Note that by deleting the node, we do not mean removing it from memory. We mean:
 * <p>
 * The value of the given node should not exist in the linked list.
 * The number of nodes in the linked list should decrease by one.
 * All the values before node should be in the same order.
 * All the values after node should be in the same order.
 * Custom testing:
 * <p>
 * For the input, you should provide the entire linked list head and the node to be given node. node should not be the last node of the list and should be an actual node in the list.
 * We will build the linked list and pass the node to your function.
 * The output will be the entire list after calling your function.
 * <p>
 * Input/Output: Input: [4,5,1,9], node=5
 * Output: [4,1,9]
 * Edge Cases/Boundaries: Last node cannot be deleted.
 * <p>
 * Constraints:
 * <p>
 * The number of the nodes in the given list is in the range [2, 1000].
 * -1000 <= Node.val <= 1000
 * The value of each node in the list is unique.
 * The node to be deleted is in the list and is not a tail node.
 */
public class DeleteNode {
    public static void main(String[] args) {
        DeleteNode delete = new DeleteNode();

        // Test Case 1: Delete node with value 5 (second node)
        ListNode head1 = new ListNode(4);
        head1.next = new ListNode(5);
        head1.next.next = new ListNode(1);
        head1.next.next.next = new ListNode(9);
        ListNode node1 = head1.next;  // Node with value 5
        System.out.println("Original: " + head1);
        delete.deleteNode(node1);
        System.out.println("After deleting node (val=5): " + head1);

        // Test Case 2: Delete node with value 1 (third node)
        ListNode head2 = new ListNode(4);
        head2.next = new ListNode(5);
        head2.next.next = new ListNode(1);
        head2.next.next.next = new ListNode(9);
        ListNode node2 = head2.next.next;  // Node with value 1
        System.out.println("\nOriginal: " + head2);
        delete.deleteNode(node2);
        System.out.println("After deleting node (val=1): " + head2);
    }


    public void deleteNode(ListNode node) {
        if (node == null || node.next == null) {
            return;
        }
        // Copy the value from the next node to the current node
        node.val = node.next.val;
        // Skip the next node (effectively deleting it)
        node.next = node.next.next;
    }
}
