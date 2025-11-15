package org.js.linkedlist.practice;

import java.util.Stack;

/// Problem: Given the head of a singly linked list, return true if it is a palindrome or false otherwise.
///
/// Input/Output: Input: 1->2->2->1
/// Output: true
///
/// Edge Cases/Boundaries: Odd/even length.
///
///  Constraints:
///
/// The number of nodes in the list is in the range [1, 10^5].
/// 0 <= Node.val <= 9
///
/// Follow up: Could you do it in O(n) time and O(1) space?
public class PalindromeLL {
    private static ListNode front;

    static void main() {
        // Test case 1: Even length palindrome (expected: true)
        ListNode headEven = new ListNode(1);
        headEven.next = new ListNode(2);
        headEven.next.next = new ListNode(2);
        headEven.next.next.next = new ListNode(1);

        System.out.println("Even length (reverse): " + (isPalindrome(headEven) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Even length (recursive): " + (isPalindromeRecursive(headEven) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Even length (brute): " + (isPalindromeBrute(headEven) ? "Palindrome" : "Not Palindrome"));

        // Test case 2: Odd length palindrome (expected: true)
        ListNode headOdd = new ListNode(1);
        headOdd.next = new ListNode(2);
        headOdd.next.next = new ListNode(3);
        headOdd.next.next.next = new ListNode(2);
        headOdd.next.next.next.next = new ListNode(1);

        System.out.println("Odd length (reverse): " + (isPalindrome(headOdd) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Odd length (recursive): " + (isPalindromeRecursive(headOdd) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Odd length (brute): " + (isPalindromeBrute(headOdd) ? "Palindrome" : "Not Palindrome"));

        // Test case 3: Not palindrome (expected: false)
        ListNode headNot = new ListNode(1);
        headNot.next = new ListNode(2);

        System.out.println("Not palindrome (reverse): " + (isPalindrome(headNot) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Not palindrome (recursive): " + (isPalindromeRecursive(headNot) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Not palindrome (brute): " + (isPalindromeBrute(headNot) ? "Palindrome" : "Not Palindrome"));

        // Test case 4: Single node (expected: true)
        ListNode headSingle = new ListNode(1);

        System.out.println("Single node (reverse): " + (isPalindrome(headSingle) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Single node (recursive): " + (isPalindromeRecursive(headSingle) ? "Palindrome" : "Not Palindrome"));
        System.out.println("Single node (brute): " + (isPalindromeBrute(headSingle) ? "Palindrome" : "Not Palindrome"));
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode node = reverse(slow.next);

        ListNode first = head;
        ListNode second = node;

        while (second != null) {
            if (first.val != second.val) {
                reverse(node);
                return false;
            }

            first = first.next;
            second = second.next;
        }

        reverse(node);

        return true;
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

    public static boolean isPalindromeRecursive(ListNode head) {
        if (head == null) {
            return false;
        }
        front = head;
        return recursivelyCheck(head);
    }

    private static boolean recursivelyCheck(ListNode current) {
        if (current != null) {
            if (!recursivelyCheck(current.next)) {
                return false;
            }
            if (current.val != front.val) {
                return false;
            }
            front = front.next;
        }
        return true;
    }


    public static boolean isPalindromeBrute(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode temp = head;
        Stack<Integer> stack = new Stack<>();

        while (temp != null) {
            stack.push(temp.val);
            temp = temp.next;
        }
        temp = head;
        while (temp != null) {
            if (temp.val != stack.peek()) {
                return false;
            }

            stack.pop();
            temp = temp.next;
        }
        return true;
    }
}