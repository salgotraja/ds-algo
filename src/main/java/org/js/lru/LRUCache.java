package org.js.lru;

import java.util.HashMap;
import java.util.Map;

public class LRUCache <K, V>{

    private final class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node> map;
    private final Node head, tail;
    private final Object lock = new Object();

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        synchronized (lock) {
            Node node = map.get(key);
            if (node == null) return null;
            moveToHead(node);
            return node.value;
        }
    }

    public void put(K key, V value) {
        synchronized (lock) {
            Node node = map.get(key);
            if (node != null) {
                node.value = value;
                moveToHead(node);
            } else {
                Node newNode = new Node(key, value);
                map.put(key, newNode);
                addToHead(newNode);
                if (map.size() > capacity) {
                    Node lru = popTail();
                    map.remove(lru.key);
                }
            }
        }
    }

    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private Node popTail() {
        Node last = tail.prev;
        removeNode(last);
        return last;
    }

    public int size() {
        synchronized (lock) {
            return map.size();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns a snapshot of all key-value pairs.
     * Used for data migration in distributed cache scenarios.
     */
    public Map<K, V> snapshot() {
        synchronized (lock) {
            Map<K, V> snapshot = new HashMap<>();
            Node node = head.next;
            while (node != tail) {
                snapshot.put(node.key, node.value);
                node = node.next;
            }
            return snapshot;
        }
    }

    /**
     * Removes and returns the value for the given key, or null if not present.
     */
    public V remove(K key) {
        synchronized (lock) {
            Node node = map.remove(key);
            if (node == null) return null;
            removeNode(node);
            return node.value;
        }
    }

    @Override
    public String toString() {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder("LRU[");
            Node node = head.next;
            while (node != tail) {
                sb.append(node.key).append("=").append(node.value);
                node = node.next;
                if (node != tail) sb.append(" < ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
