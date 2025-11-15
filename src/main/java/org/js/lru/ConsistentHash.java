package org.js.lru;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConsistentHash<T> {

    @FunctionalInterface
    public interface HashFunction {
        long hash(String key);
    }

    /** Fast, non-cryptographic hash (JDK-only) */
    public static final HashFunction MURMUR3_LIKE = key -> {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        long h = 0x9747b28cL;
        for (byte b : bytes) {
            h = (h ^ (b & 0xFF)) * 0x5bd1e995L;
        }
        h ^= h >> 13;
        h *= 0x5bd1e995L;
        h ^= h >> 15;
        return h;
    };

    private final HashFunction hashFunction;
    private final int replicas;
    private final ConcurrentSkipListMap<Long, T> ring = new ConcurrentSkipListMap<>();

    public ConsistentHash(HashFunction hashFunction, int replicas, Collection<T> nodes) {
        this.hashFunction = hashFunction != null ? hashFunction : MURMUR3_LIKE;
        this.replicas = replicas;
        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        String base = node.getClass().getSimpleName() + "@" + System.identityHashCode(node);
        for (int i = 0; i < replicas; i++) {
            long hash = hashFunction.hash(base + "-VN" + i);
            ring.put(hash, node);
        }
    }

    public void remove(T node) {
        String base = node.getClass().getSimpleName() + "@" + System.identityHashCode(node);
        for (int i = 0; i < replicas; i++) {
            long hash = hashFunction.hash(base + "-VN" + i);
            ring.remove(hash);
        }
    }

    public T get(Object key) {
        if (ring.isEmpty()) return null;
        long hash = hashFunction.hash(key.toString());
        SortedMap<Long, T> tail = ring.tailMap(hash);
        long targetHash = tail.isEmpty() ? ring.firstKey() : tail.firstKey();
        return ring.get(targetHash);
    }

    public int size() {
        return ring.size();
    }
}