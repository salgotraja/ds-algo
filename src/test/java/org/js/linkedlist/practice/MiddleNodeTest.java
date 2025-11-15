package org.js.linkedlist.practice;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class MiddleNodeTest {


    private final MiddleNode solver = new MiddleNode();


    private static ListNode buildList(int[] vals) {
        if (vals == null || vals.length == 0) return null;
        ListNode head = new ListNode(vals[0]);
        ListNode cur = head;
        for (int i = 1; i < vals.length; i++) {
            cur.next = new ListNode(vals[i]);
            cur = cur.next;
        }
        return head;
    }


    @Test
    @DisplayName("Odd length list returns exact middle")
    void oddLength() {
        ListNode head = buildList(new int[]{1,2,3,4,5});
        ListNode mid = solver.middleNode(head);
        assertNotNull(mid);
        assertEquals("3->4->5->null", mid.listString());
    }


    @Test
    @DisplayName("Even length list returns second middle")
    void evenLength() {
        ListNode head = buildList(new int[]{1,2,3,4});
        ListNode mid = solver.middleNode(head);
        assertNotNull(mid);
        assertEquals("3->4->null", mid.listString());
    }


    @Test
    @DisplayName("Single node list")
    void singleNode() {
        ListNode head = buildList(new int[]{7});
        ListNode mid = solver.middleNode(head);
        assertNotNull(mid);
        assertEquals("7->null", mid.listString());
    }


    @Test
    @DisplayName("Two node list returns second node")
    void twoNodes() {
        ListNode head = buildList(new int[]{8,9});
        ListNode mid = solver.middleNode(head);
        assertNotNull(mid);
        assertEquals("9->null", mid.listString());
    }


    @Test
    @DisplayName("List with duplicates")
    void duplicates() {
        ListNode head = buildList(new int[]{1,2,2,3,2});
        ListNode mid = solver.middleNode(head);
        assertEquals("2->3->2->null", mid.listString());
    }


    @Test
    @DisplayName("Large-ish list sanity")
    void largeList() {
        int n = 101; // odd length
        int[] vals = new int[n];
        for (int i = 0; i < n; i++) vals[i] = i + 1;
        ListNode head = buildList(vals);
        ListNode mid = solver.middleNode(head);
// middle should be at index 50 (1-based 51) -> value 51
        assertEquals("51->52->53->null".substring(0, 11), mid.listString().substring(0, 11));
    }
}