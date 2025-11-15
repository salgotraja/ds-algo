package org.js.lru;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ScalableDistributedLRUCache<K, V> {
    private final List<LRUCache<K, V>> shards;
    private volatile ConsistentHash<LRUCache<K, V>> ring;
    private final int capacityPerShard;
    private final Object reshardLock = new Object();

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

    private void rebuildRing() {
        this.ring = new ConsistentHash<>(ConsistentHash.MURMUR3_LIKE, 100, shards);
    }

    /**
     * Adds a new shard and rebalances data.
     * This operation is expensive as it requires checking all existing keys.
     */
    public void addShard() {
        synchronized (reshardLock) {
            LRUCache<K, V> newShard = new LRUCache<>(capacityPerShard);

            Map<K, V> allData = new HashMap<>();
            for (LRUCache<K, V> shard : shards) {
                allData.putAll(shard.snapshot());
            }

            shards.add(newShard);
            rebuildRing();

            for (LRUCache<K, V> shard : shards) {
                Map<K, V> snapshot = shard.snapshot();
                for (K key : snapshot.keySet()) {
                    shard.remove(key);
                }
            }

            for (Map.Entry<K, V> entry : allData.entrySet()) {
                LRUCache<K, V> targetShard = ring.get(entry.getKey());
                targetShard.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("key cannot be null");
        ConsistentHash<LRUCache<K, V>> currentRing = this.ring;
        LRUCache<K, V> shard = currentRing.get(key);
        return shard.get(key);
    }

    public void put(K key, V value) {
        if (key == null) throw new NullPointerException("key cannot be null");
        ConsistentHash<LRUCache<K, V>> currentRing = this.ring;
        LRUCache<K, V> shard = currentRing.get(key);
        shard.put(key, value);
    }

    public int shardCount() {
        synchronized (reshardLock) {
            return shards.size();
        }
    }

    public long totalSize() {
        return shards.stream().mapToLong(LRUCache::size).sum();
    }
}