package org.js.lru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class LRUCacheTest {

    private LRUCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(3);
    }

    @Test
    void constructor_withInvalidCapacity_throwsException() {
        assertThatThrownBy(() -> new LRUCache<String, Integer>(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Capacity must be positive");

        assertThatThrownBy(() -> new LRUCache<String, Integer>(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void put_and_get_basicOperations() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        assertThat(cache.get("a")).isEqualTo(1);
        assertThat(cache.get("b")).isEqualTo(2);
        assertThat(cache.get("c")).isEqualTo(3);
        assertThat(cache.size()).isEqualTo(3);
    }

    @Test
    void get_nonExistentKey_returnsNull() {
        assertThat(cache.get("nonexistent")).isNull();
    }

    @Test
    void put_exceedsCapacity_evictsLRU() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        // Access 'a' to make it most recently used
        cache.get("a");

        // Add new item - should evict 'b' (least recently used)
        cache.put("d", 4);

        assertThat(cache.get("b")).isNull();
        assertThat(cache.get("a")).isEqualTo(1);
        assertThat(cache.get("c")).isEqualTo(3);
        assertThat(cache.get("d")).isEqualTo(4);
        assertThat(cache.size()).isEqualTo(3);
    }

    @Test
    void put_updateExistingKey_updatesValue() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("a", 100); // Update

        assertThat(cache.get("a")).isEqualTo(100);
        assertThat(cache.size()).isEqualTo(2);
    }

    @Test
    void get_movesToMostRecentlyUsed() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        // Access 'a', making it most recently used
        cache.get("a");

        // Add 'd' - should evict 'b' (LRU)
        cache.put("d", 4);

        assertThat(cache.get("a")).isEqualTo(1); // Still present
        assertThat(cache.get("b")).isNull();     // Evicted
        assertThat(cache.get("c")).isEqualTo(3);
        assertThat(cache.get("d")).isEqualTo(4);
    }

    @Test
    void snapshot_returnsAllEntries() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        Map<String, Integer> snapshot = cache.snapshot();

        assertThat(snapshot).hasSize(3);
        assertThat(snapshot).containsEntry("a", 1);
        assertThat(snapshot).containsEntry("b", 2);
        assertThat(snapshot).containsEntry("c", 3);
    }

    @Test
    void snapshot_returnsImmutableCopy() {
        cache.put("a", 1);
        Map<String, Integer> snapshot = cache.snapshot();

        // Modifying cache should not affect snapshot
        cache.put("b", 2);

        assertThat(snapshot).hasSize(1);
        assertThat(cache.size()).isEqualTo(2);
    }

    @Test
    void remove_existingKey_removesAndReturnsValue() {
        cache.put("a", 1);
        cache.put("b", 2);

        Integer removed = cache.remove("a");

        assertThat(removed).isEqualTo(1);
        assertThat(cache.get("a")).isNull();
        assertThat(cache.size()).isEqualTo(1);
    }

    @Test
    void remove_nonExistentKey_returnsNull() {
        Integer removed = cache.remove("nonexistent");
        assertThat(removed).isNull();
    }

    @Test
    void toString_showsOrderFromMostToLeastRecent() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        String str = cache.toString();

        assertThat(str).contains("c=3");
        assertThat(str).contains("b=2");
        assertThat(str).contains("a=1");
    }

    @Test
    void concurrentPutAndGet_maintainsConsistency() throws InterruptedException {
        LRUCache<Integer, Integer> concurrentCache = new LRUCache<>(100);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        int key = threadId * 100 + j;
                        concurrentCache.put(key, key * 2);
                        Integer value = concurrentCache.get(key);
                        if (value != null && value == key * 2) {
                            successCount.incrementAndGet();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Should have high success rate (allowing some evictions)
        assertThat(successCount.get()).isGreaterThan(900);
    }

    @Test
    void capacityOne_worksCorrectly() {
        LRUCache<String, String> singleCache = new LRUCache<>(1);

        singleCache.put("a", "1");
        assertThat(singleCache.get("a")).isEqualTo("1");

        singleCache.put("b", "2");
        assertThat(singleCache.get("a")).isNull();
        assertThat(singleCache.get("b")).isEqualTo("2");
        assertThat(singleCache.size()).isEqualTo(1);
    }
}
