package org.js.linkedlist.practice;

import org.js.linkedlist.Node;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {}
    ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }


    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode temp = this;
        while (temp != null) {
            sb.append(temp.val).append("->");
            temp = temp.next;
        }
        sb.append("null");
        return sb.toString();
    }*/

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node(val=").append(val).append(", list=");
        ListNode t = this;
        while (t != null) {
            sb.append(t.val).append("->");
            t = t.next;
        }
        sb.append("null");
        sb.append(")");
        return sb.toString();
    }*/

    // utility: return a compact string of just the list portion (same as used inside toString)
    public String listString() {
        StringBuilder sb = new StringBuilder();
        ListNode t = this;
        while (t != null) {
            sb.append(t.val).append("->");
            t = t.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
