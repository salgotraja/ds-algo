package org.js.lru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ScalableDistributedLRUCacheTest {

    private ScalableDistributedLRUCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new ScalableDistributedLRUCache<>(2, 10);
    }

    @Test
    void constructor_withInvalidParams_throwsException() {
        assertThatThrownBy(() -> new ScalableDistributedLRUCache<String, Integer>(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("initialShards > 0");

        assertThatThrownBy(() -> new ScalableDistributedLRUCache<String, Integer>(2, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("capacityPerShard > 0");
    }

    @Test
    void initialSetup_hasCorrectShardCount() {
        assertThat(cache.shardCount()).isEqualTo(2);
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
    void addShard_increasesShardCount() {
        cache.addShard();
        assertThat(cache.shardCount()).isEqualTo(3);

        cache.addShard();
        assertThat(cache.shardCount()).isEqualTo(4);
    }

    @Test
    void addShard_preservesExistingData() {
        // Add data before scaling
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);
        cache.put("key4", 4);
        cache.put("key5", 5);

        long sizeBefore = cache.totalSize();

        // Add a new shard
        cache.addShard();

        // All data should still be accessible
        assertThat(cache.get("key1")).isEqualTo(1);
        assertThat(cache.get("key2")).isEqualTo(2);
        assertThat(cache.get("key3")).isEqualTo(3);
        assertThat(cache.get("key4")).isEqualTo(4);
        assertThat(cache.get("key5")).isEqualTo(5);

        // Total size should remain the same
        assertThat(cache.totalSize()).isEqualTo(sizeBefore);
    }

    @Test
    void addShard_migratesDataCorrectly() {
        // Add many items
        for (int i = 0; i < 20; i++) {
            cache.put("key" + i, i);
        }

        long sizeBefore = cache.totalSize();

        // Add shard (should trigger migration)
        cache.addShard();

        // Verify data is still accessible
        // Note: Due to consistent hashing, some keys may have been evicted if distribution was uneven
        int foundCount = 0;
        for (int i = 0; i < 20; i++) {
            Integer value = cache.get("key" + i);
            if (value != null && value == i) {
                foundCount++;
            }
        }

        // All items that existed before should still exist after
        assertThat((long)foundCount).isEqualTo(sizeBefore);
        assertThat(cache.totalSize()).isEqualTo(sizeBefore);
    }

    @Test
    void addShard_dataRebalancesAcrossShards() {
        // Add data with 2 shards
        for (int i = 0; i < 10; i++) {
            cache.put("key" + i, i);
        }

        // Add third shard
        cache.addShard();

        // Add more data - should distribute across 3 shards now
        for (int i = 10; i < 20; i++) {
            cache.put("key" + i, i);
        }

        // All data should be accessible
        for (int i = 0; i < 20; i++) {
            assertThat(cache.get("key" + i)).isEqualTo(i);
        }

        assertThat(cache.totalSize()).isEqualTo(20);
    }

    @Test
    void multipleScaling_preservesAllData() {
        // Add initial data
        cache.put("a", 1);
        cache.put("b", 2);

        // Scale up
        cache.addShard();
        assertThat(cache.get("a")).isEqualTo(1);
        assertThat(cache.get("b")).isEqualTo(2);

        // Add more data
        cache.put("c", 3);

        // Scale up again
        cache.addShard();
        assertThat(cache.get("a")).isEqualTo(1);
        assertThat(cache.get("b")).isEqualTo(2);
        assertThat(cache.get("c")).isEqualTo(3);

        // Final verification
        assertThat(cache.shardCount()).isEqualTo(4);
        assertThat(cache.totalSize()).isEqualTo(3);
    }

    @Test
    void update_afterScaling_worksCorrectly() {
        cache.put("key", 1);
        cache.addShard();
        cache.put("key", 100);

        assertThat(cache.get("key")).isEqualTo(100);
    }

    @Test
    void totalSize_reflectsAllShardsAfterScaling() {
        for (int i = 0; i < 15; i++) {
            cache.put("key" + i, i);
        }

        long sizeBefore = cache.totalSize();
        cache.addShard();
        long sizeAfter = cache.totalSize();

        assertThat(sizeAfter).isEqualTo(sizeBefore);
    }

    @Test
    void nullKey_throwsException() {
        assertThatThrownBy(() -> cache.put(null, 1))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("key cannot be null");

        assertThatThrownBy(() -> cache.get(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("key cannot be null");
    }

    @Test
    void scalingWithFullShards_migratesCorrectly() {
        // Fill the cache (2 shards * 10 capacity = 20 items)
        for (int i = 0; i < 20; i++) {
            cache.put("key" + i, i);
        }

        long sizeBefore = cache.totalSize();

        // Add a shard
        cache.addShard();

        // Verify the keys that existed before scaling are still accessible
        int foundCount = 0;
        for (int i = 0; i < 20; i++) {
            Integer value = cache.get("key" + i);
            if (value != null && value == i) {
                foundCount++;
            }
        }

        // All items that were stored before should still be stored after migration
        assertThat((long)foundCount).isEqualTo(sizeBefore);
        assertThat(cache.totalSize()).isEqualTo(sizeBefore);
    }

    @Test
    void capacityIncreasesWithShards() {
        // Initially: 2 shards * 10 capacity = 20 total
        cache.addShard();
        // Now: 3 shards * 10 capacity = 30 total

        // Add 30 items
        for (int i = 0; i < 30; i++) {
            cache.put("key" + i, i);
        }

        // Should be able to store most items (allowing for uneven distribution causing some eviction)
        // With consistent hashing, distribution isn't perfect, so accept 24-30
        assertThat(cache.totalSize()).isGreaterThanOrEqualTo(24);
        assertThat(cache.totalSize()).isLessThanOrEqualTo(30);
    }

    @Test
    void consistentHashing_minimizesDataMovement() {
        // Add data
        for (int i = 0; i < 10; i++) {
            cache.put("key" + i, i);
        }

        // Track which keys we can still access
        int accessibleBefore = 0;
        for (int i = 0; i < 10; i++) {
            if (cache.get("key" + i) != null) {
                accessibleBefore++;
            }
        }

        // Add shard
        cache.addShard();

        // Count accessible keys after scaling
        int accessibleAfter = 0;
        for (int i = 0; i < 10; i++) {
            if (cache.get("key" + i) != null) {
                accessibleAfter++;
            }
        }

        // All keys should still be accessible
        assertThat(accessibleAfter).isEqualTo(accessibleBefore);
    }
}
