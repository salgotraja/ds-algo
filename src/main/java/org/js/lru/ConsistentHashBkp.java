package org.js.lru;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConsistentHashBkp<T> {
    private final int numberOfReplicas;           // virtual nodes per real node
    private final ConcurrentSkipListMap<Long, T> ring = new ConcurrentSkipListMap<>();
    private final List<T> nodes = new ArrayList<>();

    /**
     * @param nodes             List of real nodes (e.g., LRUCache shards)
     * @param numberOfReplicas  Number of virtual nodes per real node (e.g., 100)
     */
    public ConsistentHashBkp(Collection<T> nodes, int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            addNode(node);
        }
    }

    public ConsistentHashBkp(List<T> nodes, int numberOfReplicas) {
        this((Collection<T>) nodes, numberOfReplicas);
    }

    /** Add a new node (e.g., during scale-out) */
    public void addNode(T node) {
        nodes.add(node);
        for (int i = 0; i < numberOfReplicas; i++) {
            String virtualNodeKey = node.toString() + "-VN" + i;
            long hash = hash(virtualNodeKey);
            ring.put(hash, node);
        }
    }

    /** Remove a node (e.g., during scale-in or failure) */
    public void removeNode(T node) {
        nodes.remove(node);
        for (int i = 0; i < numberOfReplicas; i++) {
            String virtualNodeKey = node.toString() + "-VN" + i;
            long hash = hash(virtualNodeKey);
            ring.remove(hash);
        }
    }

    /** Get the node responsible for the given key */
    public T getNode(Object key) {
        if (ring.isEmpty()) {
            return null;
        }

        long hash = hash(key.toString());
        SortedMap<Long, T> tailMap = ring.tailMap(hash);
        Long nextHash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        return ring.get(nextHash);
    }

    /** Murmur3 64-bit hash */
    private long hash(String key) {
        return Hashing.murmur3_128()
                .hashString(key, StandardCharsets.UTF_8)
                .asLong();
    }

    /** For debugging / monitoring */
    public int getRingSize() {
        return ring.size();
    }

    public List<T> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
}
