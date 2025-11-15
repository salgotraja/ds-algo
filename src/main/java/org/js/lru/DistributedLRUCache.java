package org.js.lru;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DistributedLRUCache<K, V> {
    private final List<LRUCache<K, V>> shards;
    private final ConsistentHash<LRUCache<K, V>> ring;

    public DistributedLRUCache(int numShards, int capacityPerShard) {
        if (numShards <= 0) throw new IllegalArgumentException("numShards > 0");
        if (capacityPerShard <= 0) throw new IllegalArgumentException("capacityPerShard > 0");

        this.shards = IntStream.range(0, numShards)
                .mapToObj(i -> new LRUCache<K, V>(capacityPerShard))
                .collect(Collectors.toUnmodifiableList());

        this.ring = new ConsistentHash<>(ConsistentHash.MURMUR3_LIKE, 100, this.shards);
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