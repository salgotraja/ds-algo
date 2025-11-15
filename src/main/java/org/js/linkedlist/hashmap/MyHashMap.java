package org.js.linkedlist.hashmap;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Arrays;

// --- 1. Node Class (Base for both Linked List and Tree) ---
class Node<K, V> {
    final int hash;
    final K key;
    V value;
    Node<K, V> next; // Used for linked list in a bucket

    Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
    public V setValue(V newValue) {
        V oldValue = this.value;
        this.value = newValue;
        return oldValue;
    }

    @Override
    public final String toString() { return key + "=" + value; }

    @Override
    public final int hashCode() { return Objects.hashCode(key) ^ Objects.hashCode(value); }

    @Override
    public final boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Node) {
            Node<?, ?> e = (Node<?, ?>) o;
            if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }
}

// --- 2. TreeNode Class (Extends Node for Red-Black Tree properties) ---
class TreeNode<K, V> extends Node<K, V> {
    TreeNode<K, V> parent;
    TreeNode<K, V> left;
    TreeNode<K, V> right;
    boolean red; // true for red, false for black

    TreeNode(int hash, K key, V value, Node<K, V> next, TreeNode<K,V> parent) {
        super(hash, key, value, next);
        this.parent = parent;
        this.red = true;
    }

    // Simplified BST insertion logic
    public void putTreeVal(MyHashMap<K, V> map, int hash, K key, V value) {
        TreeNode<K,V> x = this;
        int cmp;

        do {
            cmp = compareKeys(key, x.key);
            if (cmp < 0) {
                if (x.left == null) {
                    x.left = new TreeNode<>(hash, key, value, null, x);
                    return;
                }
                x = x.left;
            } else if (cmp > 0) {
                if (x.right == null) {
                    x.right = new TreeNode<>(hash, key, value, null, x);
                    return;
                }
                x = x.right;
            } else {
                x.value = value;
                return;
            }
        } while (true);
    }

    // Simplified BST get logic
    public Node<K, V> getTreeVal(int hash, K key) {
        TreeNode<K,V> x = this;
        int cmp;
        while (x != null) {
            cmp = compareKeys(key, x.key);
            if (cmp == 0) {
                return x;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return null;
    }

    // Helper for key comparison (simplifying - real HashMap uses Comparable and identity hashing)
    // This method needs to be able to compare MyKey if MyKey is used as a key type.
    // Making MyKey a static nested class makes it accessible here.
    private int compareKeys(K k1, K k2) {
        if (k1 instanceof Comparable && k2 instanceof Comparable) {
            return ((Comparable) k1).compareTo(k2);
        }
        return Objects.hashCode(k1) - Objects.hashCode(k2);
    }

    // For printing the tree structure (simplified inorder traversal)
    public void printTree(int indent) {
        if (right != null) {
            right.printTree(indent + 1);
        }
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println(key + (red ? " (R)" : " (B)"));
        if (left != null) {
            left.printTree(indent + 1);
        }
    }
}


// --- 3. MyHashMap Class ---
public class MyHashMap<K, V> {
    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private float loadFactor;
    private int threshold;

    // Constants (similar to Java's HashMap)
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8; // If bucket list size >= 8, convert to tree
    static final int MIN_TREEIFY_CAPACITY = 64; // Only treeify if table capacity >= 64

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
        this.size = 0;
    }

    // Simple hash function (actual HashMap has a more complex one)
    private final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // Get the index for a given hash
    private int getIndex(int hash) {
        return hash & (capacity - 1);
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    private V putVal(int hash, K key, V value) {
        if (table == null || table.length == 0) {
            resize();
        }

        int index = getIndex(hash);
        Node<K, V> p = table[index];

        if (p == null) {
            table[index] = new Node<>(hash, key, value, null);
        } else {
            Node<K, V> e;
            K k;

            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            }
            else if (p instanceof TreeNode) {
                e = ((TreeNode<K,V>)p).getTreeVal(hash, key);
                if (e != null) {
                    e.value = value;
                    return e.value;
                }
                ((TreeNode<K, V>) p).putTreeVal(this, hash, key, value);
                size++;
                return null;
            }
            else {
                int binCount = 0;
                for (e = p.next; e != null; e = e.next) {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    binCount++;
                }

                if (e != null) {
                    V oldValue = e.value;
                    e.value = value;
                    return oldValue;
                } else {
                    p.next = new Node<>(hash, key, value, null);
                    binCount++;

                    if (binCount >= TREEIFY_THRESHOLD - 1 && capacity >= MIN_TREEIFY_CAPACITY) {
                        treeifyBin(index);
                    }
                }
            }
        }

        size++;

        if (size > threshold) {
            resize();
        }
        return null;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = getIndex(hash);
        Node<K, V> p = table[index];

        if (p == null) {
            return null;
        }

        K k;
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
            return p.value;
        }

        if (p.next != null) {
            if (p instanceof TreeNode) {
                Node<K,V> node = ((TreeNode<K,V>)p).getTreeVal(hash, key);
                return node != null ? node.value : null;
            } else {
                Node<K, V> e;
                for (e = p.next; e != null; e = e.next) {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        return e.value;
                    }
                }
            }
        }
        return null;
    }

    // Corrected resize method
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Node<K, V>[] oldTable = table;

        int newCapacity = oldCapacity << 1; // Double the capacity
        if (newCapacity < oldCapacity) { // Overflow check (highly unlikely for practical sizes)
            newCapacity = Integer.MAX_VALUE; // Or throw error
        }

        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        this.capacity = newCapacity;
        this.threshold = (int) (newCapacity * loadFactor);
        this.table = newTable;

        System.out.println("--- Resizing: Old Capacity " + oldCapacity + ", New Capacity " + newCapacity + ", New Threshold " + threshold + " ---");

        if (oldTable != null) {
            for (int i = 0; i < oldCapacity; i++) {
                Node<K, V> e = oldTable[i];
                if (e != null) {
                    oldTable[i] = null; // Clear old reference

                    if (e.next == null) {
                        // Single node in the bucket, just rehash and put in new table
                        newTable[getIndex(e.hash)] = e;
                    } else if (e instanceof TreeNode) {
                        // Rehash a tree. Simplification: we'll convert it back to a list
                        // and re-treeify if needed. Real HashMap splits trees.
                        LinkedList<Node<K,V>> nodesToRehash = new LinkedList<>();
                        collectTreeNodes((TreeNode<K,V>)e, nodesToRehash);
                        for(Node<K,V> node : nodesToRehash) {
                            addNodeAfterResize(newTable, node);
                        }
                    } else {
                        // Linked list: separate into two lists if keys now map to different buckets
                        Node<K, V> loHead = null, loTail = null; // Nodes that stay in original index (e.g., 0xxxx)
                        Node<K, V> hiHead = null, hiTail = null; // Nodes that move to new index (e.g., 1xxxx)

                        Node<K, V> current = e;
                        do {
                            Node<K, V> next = current.next;
                            current.next = null; // Detach current node from its old chain

                            if ((current.hash & oldCapacity) == 0) { // Bitwise trick: if the bit corresponding to oldCapacity is 0, stays in 'lo'
                                if (loTail == null) {
                                    loHead = current; // First node in lo chain
                                } else {
                                    loTail.next = current;
                                }
                                loTail = current; // Update loTail
                            } else { // Bit is 1, moves to new 'hi' index
                                if (hiTail == null) {
                                    hiHead = current; // First node in hi chain
                                } else {
                                    hiTail.next = current;
                                }
                                hiTail = current; // Update hiTail
                            }
                            current = next;
                        } while (current != null);

                        if (loHead != null) {
                            newTable[i] = loHead;
                        }
                        if (hiHead != null) {
                            newTable[i + oldCapacity] = hiHead; // New index is old index + oldCapacity
                        }
                    }
                }
            }
        }
    }

    private void collectTreeNodes(TreeNode<K,V> node, LinkedList<Node<K,V>> list) {
        if (node == null) return;
        list.add(node);
        collectTreeNodes(node.left, list);
        collectTreeNodes(node.right, list);
    }

    private void addNodeAfterResize(Node<K,V>[] targetTable, Node<K,V> newNode) {
        int index = getIndex(newNode.hash);
        Node<K,V> existing = targetTable[index];

        if (existing == null) {
            targetTable[index] = new Node<>(newNode.hash, newNode.key, newNode.value, null);
        } else {
            Node<K,V> current = existing;
            int binCount = 0;
            while (current.next != null) {
                if (current.hash == newNode.hash && current.key.equals(newNode.key)) {
                    current.value = newNode.value;
                    return;
                }
                current = current.next;
                binCount++;
            }
            if (current.hash == newNode.hash && current.key.equals(newNode.key)) {
                current.value = newNode.value;
                return;
            }

            current.next = new Node<>(newNode.hash, newNode.key, newNode.value, null);
            binCount++;

            if (binCount >= TREEIFY_THRESHOLD -1 && capacity >= MIN_TREEIFY_CAPACITY) {
                treeifyBin(targetTable, index);
            }
        }
    }


    @SuppressWarnings("unchecked")
    private void treeifyBin(int index) {
        treeifyBin(table, index);
    }

    @SuppressWarnings("unchecked")
    private void treeifyBin(Node<K, V>[] targetTable, int index) {
        Node<K, V> head = targetTable[index];
        if (head == null) return;

        System.out.println("--- Treeifying bin at index " + index + " ---");

        TreeNode<K, V> root = null;
        Node<K, V> current = head;
        while (current != null) {
            Node<K, V> next = current.next;
            current.next = null;

            if (root == null) {
                // Cast current to TreeNode if it's the first node to become root of tree
                // This assumes `current` is already a K,V type compatible with TreeNode
                // In real HashMap, a new TreeNode object is created.
                // For simplicity, we'll create a new TreeNode using current's data.
                root = new TreeNode<>(current.hash, current.key, current.value, null, null);
                root.red = false; // Root is always black
            } else {
                root.putTreeVal(this, current.hash, current.key, current.value);
            }
            current = next;
        }
        targetTable[index] = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printMap() {
        System.out.println("\n--- MyHashMap Contents (Size: " + size + ", Capacity: " + capacity + ", Threshold: " + threshold + ") ---");
        for (int i = 0; i < table.length; i++) {
            System.out.print("Bucket " + i + ": ");
            Node<K, V> current = table[i];
            if (current == null) {
                System.out.println("Empty");
            } else if (current instanceof TreeNode) {
                System.out.println(" (Tree)");
                ((TreeNode<K,V>) current).printTree(1);
            } else {
                while (current != null) {
                    System.out.print(current.key + "=" + current.value + " -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
        System.out.println("----------------------------------------------");
    }

    // --- MyKey: Moved this class outside main, as a static nested class ---
    static class MyKey implements Comparable<MyKey> {
        String name;
        int hashOverride;

        MyKey(String name, int hashOverride) {
            this.name = name;
            this.hashOverride = hashOverride;
        }

        @Override
        public int hashCode() {
            return hashOverride;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyKey myKey = (MyKey) o;
            return Objects.equals(name, myKey.name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(MyKey other) {
            return this.name.compareTo(other.name);
        }
    }


    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        System.out.println("--- Basic Puts ---");
        map.put("apple", 10);
        map.put("banana", 20);
        map.put("cherry", 30);
        map.printMap();
        System.out.println("Get 'banana': " + map.get("banana"));
        System.out.println("Get 'grape': " + map.get("grape"));

        System.out.println("\n--- Demonstrating Resizing ---");
        for (int i = 0; i < 15; i++) {
            map.put("key" + i, i * 100);
        }
        map.printMap();

        System.out.println("\n--- Demonstrating Treeification ---");
        // Now MyKey is accessible because it's a static nested class of MyHashMap
        MyHashMap<MyKey, String> collisionMap = new MyHashMap<>();

        System.out.println("Adding colliding keys (all hash to 0 for initial capacity 16)");
        for (int i = 0; i < 10; i++) {
            // Note: If you run this multiple times quickly, sometimes the JVM can optimize
            // String.hashCode() to be slightly different across runs for the exact same string.
            // For consistent collision, explicitly use the hashOverride.
            collisionMap.put(new MyKey("CollisionKey-" + i, 0), "Value-" + i);
            if (i == TREEIFY_THRESHOLD - 2) {
                System.out.println("Map state after " + (i+1) + " colliding entries (should be list):");
                collisionMap.printMap();
            }
        }
        System.out.println("Map state after treeification (should be tree at bucket 0):");
        collisionMap.printMap();

        System.out.println("Getting 'CollisionKey-5': " + collisionMap.get(new MyKey("CollisionKey-5", 0)));
        System.out.println("Getting 'CollisionKey-9': " + collisionMap.get(new MyKey("CollisionKey-9", 0)));

        System.out.println("\n--- Put on existing treeified bucket ---");
        collisionMap.put(new MyKey("CollisionKey-10", 0), "Value-10");
        collisionMap.printMap();
        System.out.println("Getting 'CollisionKey-10': " + collisionMap.get(new MyKey("CollisionKey-10", 0)));

        System.out.println("\n--- Resizing a map with a treeified bucket ---");
        for(int i = 0; i < 20; i++) {
            collisionMap.put(new MyKey("UniqueKey-" + i, Objects.hash("UniqueKey-" + i)), "UniqueValue-" + i);
        }
        collisionMap.printMap();
    }
}