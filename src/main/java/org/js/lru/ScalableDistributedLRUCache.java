package org.js.lru;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ScalableDistributedLRUCache<K, V> {
    private final List<LRUCache<K, V>> shards;
    private ConsistentHash<LRUCache<K, V>> ring;
    private final int capacityPerShard;

    public ScalableDistributedLRUCache(int initialShards, int capacityPerShard) {
        if (initialShards <= 0) throw new IllegalArgumentException("initialShards > 0");
        if (capacityPerShard <= 0) throw new IllegalArgumentException("capacityPerShard > 0");

        this.capacityPerShard = capacityPerShard;
        this.shards = new ArrayList<>();

        for (int i = 0; i < initialShards; i++) {
            shards.add(new LRUCache<>(capacityPerShard));
        }
        rebuildRing();
    }

    private synchronized void rebuildRing() {
        this.ring = new ConsistentHash<>(ConsistentHash.MURMUR3_LIKE, 100, shards);
    }

    public synchronized void addShard() {
        LRUCache<K, V> newShard = new LRUCache<>(capacityPerShard);
        shards.add(newShard);
        rebuildRing();
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("key cannot be null");
        LRUCache<K, V> shard = ring.get(key);
        return shard.get(key);
    }

    public void put(K key, V value) {
        if (key == null) throw new NullPointerException("key cannot be null");
        LRUCache<K, V> shard = ring.get(key);
        shard.put(key, value);
    }

    public int shardCount() {
        return shards.size();
    }

    public long totalSize() {
        return shards.stream().mapToLong(LRUCache::size).sum();
    }
}