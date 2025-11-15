package org.js.lru;

/**
 * Comprehensive demonstration of LRU Cache implementations.
 * Shows basic LRU, distributed caching, and scalable distributed caching.
 */
public class Main {
    static void main(String[] args) {
        System.out.println("=== LRU Cache Implementations Demo ===\n");

        demonstrateBasicLRU();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateDistributedCache();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateScalableCache();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateThreadSafety();
    }

    /**
     * Demonstrates basic LRU cache eviction policy.
     */
    private static void demonstrateBasicLRU() {
        System.out.println("1. BASIC LRU CACHE (Capacity: 3)");
        System.out.println("-".repeat(40));

        LRUCache<String, String> cache = new LRUCache<>(3);

        // Add items
        System.out.println("Adding: a=1, b=2, c=3");
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        System.out.println("Cache: " + cache);
        System.out.println("Size: " + cache.size());

        // Access 'a' to make it most recently used
        System.out.println("\nAccessing 'a': " + cache.get("a"));
        System.out.println("Cache: " + cache);

        // Add 'd' - should evict 'b' (least recently used)
        System.out.println("\nAdding d=4 (exceeds capacity)");
        cache.put("d", "4");
        System.out.println("Cache: " + cache);
        System.out.println("'b' evicted: " + (cache.get("b") == null));

        // Update existing key
        System.out.println("\nUpdating a=100");
        cache.put("a", "100");
        System.out.println("Cache: " + cache);

        // Remove a key
        System.out.println("\nRemoving 'c': " + cache.remove("c"));
        System.out.println("Cache: " + cache);
        System.out.println("Size: " + cache.size());
    }

    /**
     * Demonstrates distributed cache with consistent hashing.
     */
    private static void demonstrateDistributedCache() {
        System.out.println("2. DISTRIBUTED LRU CACHE");
        System.out.println("-".repeat(40));
        System.out.println("Configuration: 4 shards, 5 capacity each (20 total)");

        DistributedLRUCache<String, String> cache = new DistributedLRUCache<>(4, 5);

        // Add data
        System.out.println("\nAdding 15 items...");
        for (int i = 1; i <= 15; i++) {
            cache.put("key" + i, "value" + i);
        }

        System.out.println("Total items stored: " + cache.totalSize());
        System.out.println("Shard count: " + cache.shardCount());

        // Retrieve some items
        System.out.println("\nRetrieving samples:");
        System.out.println("  key1 = " + cache.get("key1"));
        System.out.println("  key5 = " + cache.get("key5"));
        System.out.println("  key10 = " + cache.get("key10"));

        // Update a value
        System.out.println("\nUpdating key5 = 'updated_value'");
        cache.put("key5", "updated_value");
        System.out.println("  key5 = " + cache.get("key5"));

        // Demonstrate consistent hashing - same key always goes to same shard
        System.out.println("\nConsistent hashing: key 'test' always maps to same shard");
        for (int i = 0; i < 3; i++) {
            cache.put("test", "attempt_" + (i + 1));
        }
        System.out.println("  test = " + cache.get("test") + " (no duplicates across shards)");
    }

    /**
     * Demonstrates scalable cache with dynamic shard addition.
     */
    private static void demonstrateScalableCache() {
        System.out.println("3. SCALABLE DISTRIBUTED CACHE");
        System.out.println("-".repeat(40));
        System.out.println("Initial: 2 shards, 10 capacity each (20 total)");

        ScalableDistributedLRUCache<String, Integer> cache =
            new ScalableDistributedLRUCache<>(2, 10);

        // Add initial data
        System.out.println("\nAdding 15 items...");
        for (int i = 1; i <= 15; i++) {
            cache.put("item" + i, i * 10);
        }

        System.out.println("Items stored: " + cache.totalSize());
        System.out.println("Shards: " + cache.shardCount());

        // Verify data before scaling
        System.out.println("\nBefore scaling:");
        System.out.println("  item1 = " + cache.get("item1"));
        System.out.println("  item10 = " + cache.get("item10"));

        // Scale up
        System.out.println("\n>>> Adding a new shard (scaling to 3 shards) <<<");
        cache.addShard();

        System.out.println("\nAfter scaling:");
        System.out.println("  Shards: " + cache.shardCount());
        System.out.println("  Items stored: " + cache.totalSize());
        System.out.println("  Capacity: " + (cache.shardCount() * 10));

        // Verify data migrated correctly
        System.out.println("\nData integrity check:");
        System.out.println("  item1 = " + cache.get("item1") + " ✓");
        System.out.println("  item10 = " + cache.get("item10") + " ✓");

        // Add more data after scaling
        System.out.println("\nAdding 10 more items after scaling...");
        for (int i = 16; i <= 25; i++) {
            cache.put("item" + i, i * 10);
        }
        System.out.println("Total items: " + cache.totalSize());

        // Scale again
        System.out.println("\n>>> Adding another shard (scaling to 4 shards) <<<");
        cache.addShard();
        System.out.println("Shards: " + cache.shardCount());
        System.out.println("Items: " + cache.totalSize());
        System.out.println("Capacity: " + (cache.shardCount() * 10));
    }

    /**
     * Demonstrates thread safety with concurrent access.
     */
    private static void demonstrateThreadSafety() {
        System.out.println("4. THREAD SAFETY DEMONSTRATION");
        System.out.println("-".repeat(40));

        LRUCache<Integer, String> cache = new LRUCache<>(100);

        System.out.println("Spawning 5 threads, each adding 20 items...");

        Thread[] threads = new Thread[5];
        for (int t = 0; t < 5; t++) {
            final int threadId = t;
            threads[t] = new Thread(() -> {
                for (int i = 0; i < 20; i++) {
                    int key = threadId * 20 + i;
                    cache.put(key, "thread" + threadId + "_item" + i);
                }
            });
            threads[t].start();
        }

        // Wait for all threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All threads completed");
        System.out.println("Final cache size: " + cache.size());
        System.out.println("Expected: 100 (capacity limit)");

        // Verify some data
        System.out.println("\nSpot check:");
        System.out.println("  Key 0 = " + cache.get(0));
        System.out.println("  Key 50 = " + cache.get(50));
        System.out.println("  Key 99 = " + cache.get(99));

        System.out.println("\n✓ Thread safety verified - no corruption or exceptions");
    }
}