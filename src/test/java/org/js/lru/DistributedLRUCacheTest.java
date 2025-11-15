package org.js.lru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

class DistributedLRUCacheTest {

    private DistributedLRUCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new DistributedLRUCache<>(4, 10);
    }

    @Test
    void constructor_withInvalidParams_throwsException() {
        assertThatThrownBy(() -> new DistributedLRUCache<String, Integer>(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("numShards > 0");

        assertThatThrownBy(() -> new DistributedLRUCache<String, Integer>(4, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("capacityPerShard > 0");
    }

    @Test
    void put_and_get_basicOperations() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        assertThat(cache.get("a")).isEqualTo(1);
        assertThat(cache.get("b")).isEqualTo(2);
        assertThat(cache.get("c")).isEqualTo(3);
    }

    @Test
    void put_withNullKey_throwsException() {
        assertThatThrownBy(() -> cache.put(null, 1))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("key cannot be null");
    }

    @Test
    void get_withNullKey_throwsException() {
        assertThatThrownBy(() -> cache.get(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("key cannot be null");
    }

    @Test
    void shardCount_returnsCorrectCount() {
        assertThat(cache.shardCount()).isEqualTo(4);
    }

    @Test
    void distributesKeysAcrossShards() {
        // Add many keys to ensure distribution
        for (int i = 0; i < 100; i++) {
            cache.put("key" + i, i);
        }

        // Verify keys are retrievable (some may be evicted due to uneven distribution)
        int foundCount = 0;
        for (int i = 0; i < 100; i++) {
            Integer value = cache.get("key" + i);
            if (value != null && value == i) {
                foundCount++;
            }
        }

        // Most keys should be present (at least 35 out of 40 capacity)
        // Due to consistent hashing, distribution may not be perfectly even
        assertThat(foundCount).isGreaterThanOrEqualTo(35);
        assertThat(cache.totalSize()).isLessThanOrEqualTo(40);
    }

    @Test
    void totalSize_reflectsAllShards() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        assertThat(cache.totalSize()).isEqualTo(3);
    }

    @Test
    void update_existingKey_updatesValue() {
        cache.put("key", 1);
        cache.put("key", 100);

        assertThat(cache.get("key")).isEqualTo(100);
    }

    @Test
    void exceedingShardCapacity_evictsLRU() {
        // With 4 shards of capacity 10, we can store 40 items
        // But due to consistent hashing, not all shards may be equally filled

        // Add items until we exceed capacity of at least one shard
        for (int i = 0; i < 50; i++) {
            cache.put("key" + i, i);
        }

        // Some items should have been evicted
        assertThat(cache.totalSize()).isLessThanOrEqualTo(40);
    }

    @Test
    void consistentHashing_sameKeyGoesToSameShard() {
        cache.put("test", 1);
        cache.put("test", 2);
        cache.put("test", 3);

        assertThat(cache.get("test")).isEqualTo(3);
        assertThat(cache.totalSize()).isEqualTo(1);
    }

    @Test
    void concurrentAccess_maintainsConsistency() throws InterruptedException {
        DistributedLRUCache<String, Integer> largeCache = new DistributedLRUCache<>(4, 100);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        int key = threadId * 20 + j;
                        largeCache.put("key" + key, key);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Verify that values are consistent (200 items, capacity 400)
        int foundCount = 0;
        for (int i = 0; i < 200; i++) {
            Integer value = largeCache.get("key" + i);
            if (value != null && value == i) {
                foundCount++;
            }
        }

        // Most values should be present (allowing for some eviction due to uneven distribution)
        assertThat(foundCount).isGreaterThan(150);
    }

    @Test
    void multipleShards_balancesLoad() {
        DistributedLRUCache<String, Integer> multiShardCache = new DistributedLRUCache<>(8, 10);

        // Add 40 items with higher per-shard capacity to reduce eviction probability
        for (int i = 0; i < 40; i++) {
            multiShardCache.put("key" + i, i);
        }

        // Verify most items are stored
        // With 8 shards * 10 capacity = 80 total, we should store most/all 40 items
        // Allow some margin for hash distribution variance
        assertThat(multiShardCache.totalSize()).isGreaterThanOrEqualTo(35);
        assertThat(multiShardCache.totalSize()).isLessThanOrEqualTo(40);

        // Verify we can retrieve at least some items correctly
        int foundCount = 0;
        for (int i = 0; i < 40; i++) {
            Integer value = multiShardCache.get("key" + i);
            if (value != null && value == i) {
                foundCount++;
            }
        }
        assertThat(foundCount).isGreaterThanOrEqualTo(35);
    }
}
